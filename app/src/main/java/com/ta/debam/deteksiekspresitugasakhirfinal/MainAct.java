package com.ta.debam.deteksiekspresitugasakhirfinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ta.debam.deteksiekspresitugasakhirfinal.adaper.DialogAdapter;
import com.ta.debam.deteksiekspresitugasakhirfinal.adaper.PlayListAdapter;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.Ekspresi;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.History;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.Constant;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.DBHelper;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.ImageProcessing;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.KMeansClass;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.SongManager;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.Utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Debam on 5/2/2016.
 */
public class MainAct extends AppCompatActivity
        implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback{
    private SongManager mSongManager;
    private Utilities mUtil;
    private ImageProcessing imgProcess;
    private KMeansClass km;

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private ArrayList<String> songTitle;
    private ArrayList<Ekspresi> eks, cl;
    private ArrayList<String> finalEkspresi;
    private ArrayAdapter mAdapter;
    private Handler mHandler = new Handler();
    private MediaPlayer mp;
    private DBHelper mydb;

    private int currentclustering;
    private long durationTot, currentduration, clustering, checking;

    private ListView mPlayList;
    private TextView mJudul, mDuration, mCluster;
    private SeekBar songProgressBar;
    private ImageView mPictureTaken;
    private SurfaceView mSurface;

    private Bitmap takenImg, mGambar;
    private SurfaceHolder sHolder;
    private Camera mCamera;
    private Camera.Parameters parameters;

    private static final String url = "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6127337";
    private static final String user = "sql6127337";
    private static final String pass = "fb4MZzUf9p";

//    private static final String url = "jdbc:mysql://192.168.43.163:3306/ta";
//    private static final String user = "debam";
//    private static final String pass = "debam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mPlayList       = (ListView) findViewById(R.id.playlist);
        mydb            = new DBHelper(getApplicationContext());

        mJudul          = (TextView) findViewById(R.id.txt_songtitle);
        mDuration       = (TextView) findViewById(R.id.txt_duration);
        mCluster        = (TextView) findViewById(R.id.txt_sequance);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        mPictureTaken   = (ImageView) findViewById(R.id.pictureTaken);
        mSurface        = (SurfaceView) findViewById(R.id.surfaceView);

        mSongManager    = new SongManager();
        mUtil           = new Utilities();
        songTitle       = new ArrayList<>();
        mp              = new MediaPlayer();
        imgProcess      = new ImageProcessing();
        km              = new KMeansClass();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int senang=0, sedih=0, netral=0;
                String isi="";

                for(int i=0;i<finalEkspresi.size();i++){
                    if(finalEkspresi.get(i).equalsIgnoreCase("Senang")){
                        senang++;
                    } else if(finalEkspresi.get(i).equalsIgnoreCase("Sedih")){
                        sedih++;
                    } else if(finalEkspresi.get(i).equalsIgnoreCase("Netral")){
                        netral++;
                    }
                    isi = isi+"Sequence "+(i+1)+": "+finalEkspresi.get(i)+"\n";
                }

                ArrayList<Integer> getmaks = new ArrayList<Integer>();
                getmaks.add(senang);
                getmaks.add(sedih);
                getmaks.add(netral);

                int max = getmaks.get(0);
                for(int j:getmaks){
                    if(j > max) max =j;
                }

                DialogAdapter dial = new DialogAdapter();
                int warna = R.color.netral,picture = R.drawable.netral;
                String Judul="Your Expression";

                String ekspresiakhir="";
                if(max == getmaks.get(0)){
                    ekspresiakhir="Senang";
                    Judul = getString(R.string.HAPPY);
                    warna = R.color.happy;
                    picture = R.drawable.dialogue1;
                } else if(max == getmaks.get(1)){
                    ekspresiakhir="Sedih";
                    Judul = getString(R.string.SAD);
                    warna = R.color.sad;
                    picture = R.drawable.dialogue3;
                } else if(max == getmaks.get(2)){
                    ekspresiakhir="Netral";
                    Judul = getString(R.string.NETRAL);
                    warna = R.color.netral;
                    picture = R.drawable.dialogue2;
                }
                mydb.insert2history(mJudul.getText().toString(),ekspresiakhir);

                dial.showDialog(MainAct.this,Judul,isi,picture,warna);
            }
        });

        new getDB().execute();
        songsList       = mSongManager.getPlayList();

        int i =0;
        for(HashMap<String, String> lst: songsList){
            songTitle.add(i, lst.get("songTitle"));
            i++;
        };

        sHolder = mSurface.getHolder();
        sHolder.addCallback(MainAct.this);
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        PlayListAdapter PLAdapter = new PlayListAdapter(this, songTitle);
        //mAdapter        = new ArrayAdapter<String>(this, R.layout.list_titled, R.id.list_judul, songTitle);
        mPlayList.setAdapter(PLAdapter);
        mPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mJudul.setText(songTitle.get(position).toString());
                playSong(position);
            }
        });

    }

    public void  playSong(int songIndex){

        Log.i("SongPath", songsList.get(songIndex).get("songPath"));
        currentclustering =8;
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();

            finalEkspresi = new ArrayList<String>();

            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            durationTot = mp.getDuration();
            clustering = (durationTot/8)/1000;
            checking = clustering;
            mCluster.setText(currentclustering + "");

//            takingPictures();
            updateProgressBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoHistory(View v){
        Intent i = new Intent(MainAct.this, HistoryAct.class);
        startActivity(i);
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask =  new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            if((currentDuration/1000)==checking){
                takingPictures();
                checking += clustering;
                currentclustering--;
                mCluster.setText(currentclustering+"");
            }

            if(currentDuration==totalDuration){
                Log.i("CC", "Masuk SIni");
            }

            mDuration.setText(""+mUtil.milliSecondsToTimer(currentDuration));

            int progress = (int)(mUtil.getProgressPercentage(currentDuration, totalDuration));
            songProgressBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = mUtil.progressToTimer(seekBar.getProgress(), totalDuration);

        mp.seekTo(currentPosition);

        updateProgressBar();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("onCompletion","Masuk SIni");
        playSong(0);
    }

    public void takingPictures(){
        //mCamera = Camera.open(1);
        mCamera.startPreview();
        Camera.PictureCallback mCall = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                takenImg = BitmapFactory.decodeByteArray(data, 0, data.length);
                takenImg = imgProcess.RotateBitmap(takenImg, 270);
                mGambar = takenImg;

                mPictureTaken.setImageBitmap(mGambar);
            }
        };

        mCamera.takePicture(null, null, mCall);

        if(mGambar != null){
            mHandler.postDelayed(mGetDataFacec, 1000);
        }
    }

    private Runnable mGetDataFacec = new Runnable() {
        @Override
        public void run() {
            Ekspresi mEkspresi = imgProcess.GetDataBitmap(getApplicationContext(), mGambar, eks.size());
            if(!mEkspresi.getBibir().equalsIgnoreCase("no data")){
                eks.add(mEkspresi);
                if(!eks.get(eks.size()-1).getBibir().equalsIgnoreCase("no data")) {
                    mHandler.postDelayed(setKmeans, 2000);
                }
            }
        }
    };

    private Runnable setKmeans = new Runnable() {
        @Override
        public void run() {
            km.calcKmeans(eks, cl);
            mHandler.postDelayed(getEkspresi,2000);
        }
    };

    private Runnable IterasiKmeans = new Runnable() {
        @Override
        public void run() {
            km.iterasi(eks,cl);
            mHandler.postDelayed(getEkspresi,2000);
        }
    };

    private Runnable getEkspresi = new Runnable() {
        @Override
        public void run() {
            String ekspresi ="";
            if(km.cls().equalsIgnoreCase("C1")){
                ekspresi = "Senang";
            } else if(km.cls().equalsIgnoreCase("C2")){
                ekspresi = "Sedih";
            } else if(km.cls().equalsIgnoreCase("C3")){
                ekspresi = "Netral";
            }
            finalEkspresi.add(ekspresi);
            mCluster.setText(ekspresi);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open(1);
        try {
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        parameters = mCamera.getParameters();
        mCamera.setParameters(parameters);
        mCamera.startPreview();

//        Camera.PictureCallback mCall = new Camera.PictureCallback() {
//            @Override
//            public void onPictureTaken(byte[] data, Camera camera) {
//                takenImg = BitmapFactory.decodeByteArray(data, 0, data.length);
//                takenImg = imgProcess.RotateBitmap(takenImg, 270);
//                mGambar = takenImg;
//
//                mPictureTaken.setImageBitmap(mGambar);
//            }
//        };
//        mCamera.takePicture(null, null, mCall);
    }

    public void back2tutorial(View v){
        Intent i = new Intent(MainAct.this,IntroAct.class);
        Utilities.saveBoolean(getApplicationContext(),Constant.SharedPref.LOGGED, false);
        Utilities.saveBoolean(getApplicationContext(), Constant.SharedPref.BACK, true);
        startActivity(i);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    class getDB extends AsyncTask<Void, Void, ArrayList<Ekspresi>> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainAct.this);

            pDialog.setMessage("Fetching Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Ekspresi> doInBackground(Void... params) {
            ArrayList<Ekspresi> CEks = new ArrayList<Ekspresi>();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from ekspresi order by _id");

                while(rs.next()){
                    Ekspresi es = new Ekspresi();
                    es.setId(rs.getInt("_id"));
                    es.setNama(rs.getString("name"));
                    es.setKanan(rs.getString("righteye"));
                    es.setKiri(rs.getString("lefteye"));
                    es.setBibir(rs.getString("smiling"));
                    String arrayAlis = rs.getString("lips");
                    //Log.i("alis",arrayAlis);
                    int[] als = new int[80*48];
                    String[] StrAlis = arrayAlis.split("[,]");
                    for(int i=0;i<StrAlis.length;i++){
                        als[i]=Integer.parseInt(StrAlis[i]);
                    }
                    es.setBibirPixel(als);
//                    es.setAlis(new int[]{Integer.parseInt((rs.getString("alis")))});
                    CEks.add(es);
                }

                rs.close();
                con.close();
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return CEks;
        }

        @Override
        protected void onPostExecute(ArrayList<Ekspresi> ekspresis) {
            eks=ekspresis;

            cl = new ArrayList<Ekspresi>();
            Ekspresi EkCl1 = eks.get(17);
            cl.add(EkCl1);

            Ekspresi EkCl2 = eks.get(36);
            cl.add(EkCl2);

            Ekspresi EkCl3 = eks.get(58);
            cl.add(EkCl3);

            pDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
