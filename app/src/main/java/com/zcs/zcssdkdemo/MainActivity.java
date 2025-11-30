package com.zcs.zcssdkdemo;


import static com.zcs.sdk.DriverManager.getInstance;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcs.sdk.Beeper;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.Sys;
import com.zcs.sdk.card.CardInfoEntity;
import com.zcs.sdk.card.CardReaderManager;
import com.zcs.sdk.card.CardReaderTypeEnum;
import com.zcs.sdk.card.CardSlotNoEnum;
import com.zcs.sdk.card.ICCard;
import com.zcs.sdk.card.MagCard;
import com.zcs.sdk.listener.OnSearchCardListener;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.zcs.zcssdkdemo.APICallback;


public class MainActivity<BufferedImage> extends AppCompatActivity  implements APICallback {

    @Override
    public void onSuccess(String responseData) {
        // Handle successful response here
        System.out.println("API response: " + responseData);
    }

    @Override
    public void onFailure() {
        // Handle failure case here
        System.out.println("API call failed.");
    }

    public static final byte[] APDU_SELECT = {
            (byte)0x00, // CLA
            (byte)0xA4, // INS
            (byte)0X04, // P1
            (byte)0x00, // P2
            (byte)0x08, // Lc
            (byte)0xA0, (byte)0X00, (byte)0x00, (byte)0x00, (byte)0x54, (byte)0x48, (byte)0x00, (byte)0x01 // Data field
    };
    public static final byte[] APDU_CID = {
            (byte) 0x80, (byte)0xb0, (byte)0x00, (byte)0x04, (byte)0x02, (byte)0x00, (byte)0x0d
    };
    public static final byte[] APDU_FULLNAME_TH = {
            (byte)0x80, (byte)0xB0, (byte)0x00, (byte)0x11, (byte)0x02, (byte)0x00, (byte)0x64
    };
    public static final byte[] APDU_FULLNAME_EN = {
            (byte)0x80, (byte)0xB0, (byte)0x00, (byte)0x75, (byte)0x02, (byte)0x00, (byte)0x64
    };
    public static final byte[] APDU_DATE_OF_BIRTH = {
            (byte)0x80, (byte)0xB0, (byte)0x00, (byte)0xD9, (byte)0x02, (byte)0x00, (byte)0x08
    };
    public static final byte[] APDU_GENDER = {
            (byte)0x80, (byte)0xB0, (byte)0x00, (byte)0xE1, (byte)0x02, (byte)0x00, (byte)0x01
    };

    public static final byte[] APDU_CARD_ISSUER = {
            (byte)0x80, (byte)0xB0, (byte)0x00, (byte)0xF6, (byte)0x02, (byte)0x00, (byte)0x64
    };

    public static final byte[] APDU_ISSUE_DATE = {
            (byte)0x80, (byte)0xB0, (byte)0x01, (byte)0x67, (byte)0x02, (byte)0x00, (byte)0x08
    };

    public static final byte[] APDU_EXPIRE_DATE = {
            (byte)0x80, (byte)0xB0, (byte)0x01, (byte)0x6F, (byte)0x02, (byte)0x00, (byte)0x08
    };

    public static final byte[] APDU_ADDRESS = {
            (byte)0x80, (byte)0xB0, (byte)0x15, (byte)0x79, (byte)0x02, (byte)0x00, (byte)0x64
    };

    public static final byte[] APDU_ADDRESS_02 = {
            (byte)0x80, (byte)0xB0, (byte)0x15, (byte)0x79, (byte)0x02, (byte)0x00, (byte)0xA0
    };
    public static final byte[] APDU_PHOTO_PART01 = {
            (byte)0x80, (byte)0xB0, (byte)0x01, (byte)0x7B, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART02 = {
            (byte)0x80, (byte)0xB0, (byte)0x02, (byte)0x7A, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART03 = {
            (byte)0x80, (byte)0xB0, (byte)0x03, (byte)0x79, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART04 = {
            (byte)0x80, (byte)0xB0, (byte)0x04, (byte)0x78, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART05 = {
            (byte)0x80, (byte)0xB0, (byte)0x05, (byte)0x77, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART06 = {
            (byte)0x80, (byte)0xB0, (byte)0x06, (byte)0x76, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART07 = {
            (byte)0x80, (byte)0xB0, (byte)0x07, (byte)0x75, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART08 = {
            (byte)0x80, (byte)0xB0, (byte)0x08, (byte)0x74, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART09 = {
            (byte)0x80, (byte)0xB0, (byte)0x09, (byte)0x73, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART10 = {
            (byte)0x80, (byte)0xB0, (byte)0x0A, (byte)0x72, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART11 = {
            (byte)0x80, (byte)0xB0, (byte)0x0B, (byte)0x71, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART12 = {
            (byte)0x80, (byte)0xB0, (byte)0x0C, (byte)0x70, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART13 = {
            (byte)0x80, (byte)0xB0, (byte)0x0D, (byte)0x6F, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART14 = {
            (byte)0x80, (byte)0xB0, (byte)0x0E, (byte)0x6E, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART15 = {
            (byte)0x80, (byte)0xB0, (byte)0x0F, (byte)0x6D, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART16 = {
            (byte)0x80, (byte)0xB0, (byte)0x10, (byte)0x6C, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART17 = {
            (byte)0x80, (byte)0xB0, (byte)0x11, (byte)0x6B, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART18 = {
            (byte)0x80, (byte)0xB0, (byte)0x12, (byte)0x6A, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART19 = {
            (byte)0x80, (byte)0xB0, (byte)0x13, (byte)0x69, (byte)0x02, (byte)0x00, (byte)0xFF
    };

    public static final byte[] APDU_PHOTO_PART20 = {
            (byte)0x80, (byte)0xB0, (byte)0x14, (byte)0x68, (byte)0x02, (byte)0x00, (byte)0xFF
    };

//***************************************************************************************
    public static final byte[] APDU_SEND_SELECT_FILE = new byte[]{0, -92, 4, 0, 8, -96, 0, 0, 0, 84, 72, 0, 1};
    public static final byte[] APDU_SEND_GET_RESPONSE = new byte[]{0, -60, 0, 0};
    public static final byte[] APDU_SEND_GET_CID = new byte[]{-128, -80, 0, 4, 2, 0, 13};
    public static final byte[] APDU_SEND_GET_TH_FULLNAME = new byte[]{-128, -80, 0, 17, 2, 0, 100};
    public static final byte[] APDU_SEND_GET_EN_FULLNAME = new byte[]{-128, -80, 0, 117, 2, 0, 100};
    public static final byte[] APDU_SEND_GET_DATE_OF_BIRTH = new byte[]{-128, -80, 0, -39, 2, 0};
    public static final byte[] APDU_SEND_GET_DATA1 = new byte[]{-128,-80,0,4,2,0,-1};
    public static final byte[] APDU_SEND_GET_DATA2 = new byte[]{-128,-80,1,3,2,0,116};
    public static final byte[] APDU_SEND_GET_DATA3 = new byte[]{-128,-80,21,121,2,0,-82};

    //***********************************************************************************
    private static final String KEY_IC_CARD = "IC_card_key";
    private ProgressDialog mProgressDialog;
    private CardReaderManager mCardReadManager;
    private static final int READ_TIMEOUT = 60 * 1000;
    private static char[] ThaiIdDL = new char[18];
    private void searchMagnetCard() {
        showSearchCardDialog(R.string.waiting,R.string.msg_magnet_card);
        mCardReadManager.cancelSearchCard();
        mCardReadManager.searchCard(CardReaderTypeEnum.MAG_CARD, READ_TIMEOUT, mMagnetCardSearchCardListener);

    }
    private OnSearchCardListener mMagnetCardSearchCardListener = new OnSearchCardListener() {
        @Override
        public void onCardInfo(CardInfoEntity cardInfoEntity) {
            mProgressDialog.dismiss();
            readMagnetCard();
        }

        @Override
        public void onError(int i) {
            mProgressDialog.dismiss();
            showReadMagnetCardErrorDialog(i);
        }

        @Override
        public void onNoCard(CardReaderTypeEnum cardReaderTypeEnum, boolean b) {

        }
    };




    private void showSearchCardDialog(@StringRes int title, @StringRes int msg) {
        mProgressDialog = (ProgressDialog) DialogUtils.showProgress(MainActivity.this, getString(title), getString(msg), new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mCardReadManager.cancelSearchCard();

            }
        });
    }

    private void showReadICCardErrorDialog(final int errorCode) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtils.show(MainActivity.this, "Read IC card failed", "Error code = " + errorCode);
            }
        });
    }
    private void readMagnetCard() {
        CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
        MagCard magCard = mCardReadManager.getMAGCard();
        final CardInfoEntity cardInfoEntity = magCard.getMagReadData();


        if (cardInfoEntity.getResultcode() == SdkResult.SDK_OK) {
           MainActivity.this.runOnUiThread(new Runnable() {
            //new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    DialogUtils.show(MainActivity.this, "ข้อมูล", cardInfoToString(cardInfoEntity));


                }
           });

        } else {
            showReadMagnetCardErrorDialog(cardInfoEntity.getResultcode());
        }
        magCard.magCardClose();
    }
    private String cardInfoToString(CardInfoEntity cardInfoEntity) {
        if (cardInfoEntity == null)
            return null;
        Beeper mBeeper = mDriverManager.getBeeper();
        int ret = mBeeper.beep(2000, 300);
        StringBuilder sb = new StringBuilder();
        sb.append("").append(cardInfoEntity.getCardNo() == null ? "" : "รหัสบัตรประชาชน :"+covertThaiIdDL(cardInfoEntity.getCardNo())+"\n" )
                .append(cardInfoEntity.getTk1() == null ? "" : "ชื่อภาษาอังกฤษ : \n"+swapString(replaceString(cardInfoEntity.getTk1()))+"");

       String SS = sb.toString();
        tv_Result.setText(""+SS);

        //String shareFact = tv_Result.getText().toString();
        //printText(shareFact);
        bt_CancelDrivingLicence.setText("ข้อมูลใบขับขี่");

        ImageView imageView2 = findViewById(R.id.iv_Photo);
        boolean CheckPhotoboolean = hasImage(imageView2);
        if((CheckPhotoboolean==true)&&(CheckPhoto==1)){
            ((ViewGroup) imageView2.getParent()).removeView(imageView2);
            imageView2.setImageDrawable(null);
            imageView2 = null;
            CheckPhoto = 0;
           // tv_Result.setText(null);
        }

        final String S_NAME_EN;
        final String S_ID_Thai;

        S_NAME_EN = swapString(replaceString(cardInfoEntity.getTk1()));
        S_ID_Thai = covertThaiIdDL(cardInfoEntity.getCardNo());

        callUploadLog(S_ID_Thai, "", S_NAME_EN, "", "", "", 4, "099", null);

        return sb.toString();
    }
    private void showReadMagnetCardErrorDialog(final int errorCode) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtils.show(MainActivity.this, "Read magnetic card failed", "Error code = " + errorCode);
            }
        });
    }

    //***************************************************************************************
    private Button bt_readThaiID;
    private Button bt_readDrivingLicence;
    private Button bt_printSlip;
    private Button bt_Exit;
    private Button bt_Cancel;
    private static Button bt_CancelDrivingLicence;
    public static TextView tv_Result;
    public static int CheckPhoto=0;
    private Printer mPrinter;
    //*****************************************************************************************
    private ActionBar actionBar;
    private static DriverManager mDriverManager;
    private ImageView imageView_iccard;
    private Sys mSys;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override



    protected void onCreate(Bundle savedInstanceState) {


        final ImageView iv_Photo = (ImageView) findViewById(R.id.iv_Photo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.demo_title));
        }
        Fragment fragment = new SettingsFragment();
        mDriverManager = getInstance();
        mSys = mDriverManager.getBaseSysDevice();
        initSdk();
/////////////////////////////////////////////////////////////////////////////
        mPrinter = mDriverManager.getPrinter();
        /*================= Set Variable =============================*/
        bt_Cancel = findViewById(R.id.bt_Cancel);
        tv_Result = findViewById(R.id.tv_Result);
        bt_CancelDrivingLicence = findViewById(R.id.bt_CancelDrivingLicence);
        bt_CancelDrivingLicence.setVisibility(View.GONE);
        /*================= readThaiID=============================*/
        bt_readThaiID = findViewById(R.id.bt_readThaiID);
        bt_readThaiID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_CancelDrivingLicence.setText("บัตรประชาชน");
                CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
                ICCard icCard = mCardReadManager.getICCard();
                int result = icCard.icCardReset(CardSlotNoEnum.SDK_ICC_USERCARD);
                if (result == SdkResult.SDK_OK) {
                    Beeper mBeeper = mDriverManager.getBeeper();
                    int ret = mBeeper.beep(4000, 600);
                    byte[] ID_AUU = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_SELECT);
                    byte[] ID_Thai = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_CID);
                    byte[] NAME_EN = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_FULLNAME_EN);
                    byte[] NAME_TH = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_FULLNAME_TH);
                    byte[] ADDRESS  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_ADDRESS);
                    byte[] ADDRESS_02  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_ADDRESS_02);
                    byte[] S_APDU_SEND_SELECT_FILE = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_SEND_SELECT_FILE);
                    byte[] S_APDU_SEND_GET_RESPONSE = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_SEND_GET_RESPONSE);
                    byte[] S_APDU_SEND_GET_CID = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_SEND_GET_CID);
                    byte[] S_APDU_SEND_GET_TH_FULLNAME = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_SEND_GET_TH_FULLNAME);
                    byte[] BD  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_DATE_OF_BIRTH);
                    byte[] GENDER  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_GENDER);
                    byte[] CARD_ISSUER  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_CARD_ISSUER);
                    byte[] ISSUE_DATE  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_ISSUE_DATE);
                    byte[] EXPIRE_DATE  = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD, APDU_EXPIRE_DATE);
                    Bitmap bitmap = getPhoto(icCard);

                    imageView_iccard = findViewById(R.id.iv_Photo);
                    imageView_iccard.setImageBitmap(bitmap);


                    CheckPhoto = 1;
                    final String S_ID_AUU;
                    final String S_ID_Thai;
                    final String SS_APDU_SEND_GET_CID;
                    final String S_NAME_TH;
                    final String S_NAME_EN;
                    final String S_BD;
                    final String S_GENDER;
                    final String S_CARD_ISSUER;
                    final String S_ISSUE_DATE;
                    final String S_EXPIRE_DATE;
                    final String S_ADDRESS_2;

                    final String localcode = "025";
                    // add 2023-10-14 ; SLOT- NANKAI COMPANY LIMITED
                    //009- // PRN สมัคงาน -เก่า - เปลี่ยน ปรียาพร + สายสมร
                    //099 - สมัคงานใหม่ PRN
                    //004-โตดี-20231210
                    //008 -บริษัท ไทยเปเปอร์มิลล์ จำกัด
                    //024 - AMS
                    //025 - KBS
                    //"007"; // บริษัท ไทย ออโตโมทีฟ แอนด์ แอพพลิแอนซ์ จำกัด
                    //026 - ปิยะพาราวูด
                    //028 - หจก.นันทิญา
                    //029 - เตียงวูนไทย
                    //039- ศุภาลัย เบลล่า ระยอง
                    //040- เซนเนอริโอ Zenerio -ศุขประยูร-ดอนหัวฬ่อ - ชลบุรี
                    //091 - ระยองริเวอร์พาร์ค
                    //074 - สเตท อินดัสทรี (ไทยแลนด์)
                    //088 - จงลี่
                    //100 - ไฮโดรเเมก
                    //144 - เดอะวิลเลจ
                    //238 - SM สายสมร
                    //011 - AMS : บริษัท เอ เอ็ม เอส โซลูชั่นส์ จำกัด
                    //073 - อภิญญา ฟู้ดส์
                    //036 - ห้องเย็นโตดี
                    //078 - YANFENG AUTOMOTIVE INTERIOR SYSTEMS (THAILAND) CO., LTD.
                    //822 - nexteer automotive (thailand) limited
                    //823 - SLOT- NANKAI COMPANY LIMITED
                    //883 - สยามลวดเหล็ก
                    //636 - PKD
                    //886 - NFC - G2
                    //888 - NFCT - G1

                    //887 - TSN - C6140ac4ceb689c24f65a09ca4ec8bfb0
                    //889 - รอยัล ระยอง
                    S_ID_AUU = convertUTF8(ID_AUU);
                    S_ID_Thai = convertUTF8(ID_Thai);
                    SS_APDU_SEND_GET_CID = convertUTF8(S_APDU_SEND_GET_CID);
                    S_NAME_TH = convertUTF8(S_APDU_SEND_GET_TH_FULLNAME);
                    S_NAME_EN = convertUTF8(NAME_EN);
                    S_BD = convertUTF8(BD);
                    S_GENDER = convertUTF8(GENDER);
                    int int_GENDER = Integer.parseInt(S_GENDER);
                    final String  S_GENDER2 = covert_GENDER (int_GENDER);
                    S_CARD_ISSUER = convertUTF8(CARD_ISSUER);
                    S_ISSUE_DATE = convertUTF8(ISSUE_DATE);
                    S_EXPIRE_DATE = convertUTF8(EXPIRE_DATE);
                    S_ADDRESS_2 = convertUTF8(ADDRESS);


                    tv_Result.setText(SS_APDU_SEND_GET_CID +" "+S_NAME_TH);
                   // "\nวันเกิด :"+ S_BD
                    tv_Result.setText(""+"\n"+S_NAME_EN +""+"\n"+S_NAME_TH+"" );
                    //วันออกบัตร 2 :"+ S_ISSUE_DATE +"\nวันหมดอายุ :"+ S_EXPIRE_DATE \nเพศ :"+ S_GENDER2 + "\nที่อยู่ :"+S_ADDRESS_2


                    callUploadLog(S_ID_Thai, S_NAME_TH, S_NAME_EN, S_ADDRESS_2, String.valueOf(int_GENDER), S_BD, 3, localcode, bitmap);

                    /// TypeCard = 1 =แสดงข้อมูลบางส่วน ไม่เเสดงเลขบัตรประชาชน ไม่มีวันเกิด
                    /// TypeCard = 2 =แสดงข้อมูลทั้งหมด เเสดงเลขบัตรประชาชน
                    /// TypeCard = 3 =แสดงข้อมูลบางส่วน ไม่มีวันเกิด
                }

                icCard.icCardPowerDown(CardSlotNoEnum.SDK_ICC_USERCARD);
            }

        });
        /*================= bt_Exit=============================*/
        bt_Exit = findViewById(R.id.bt_Exit);
        bt_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);  //exit System
            }
        });
        /*================= bt_printSlip=============================*/
        bt_printSlip = findViewById(R.id.bt_printSlip);
        bt_printSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = null;
               // tv_Result.setText("087-6162271");
                //tv_Result.setClickable(false);
                String shareFact = tv_Result.getText().toString();
                printText(shareFact);
                tv_Result.setText(null);

            }
        });
        /*================= bt_readDrivingLicence=============================*/
        bt_readDrivingLicence = findViewById(R.id.bt_readDrivingLicence);
        bt_readDrivingLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_CancelDrivingLicence.setVisibility(View.VISIBLE);
                tv_Result.setText("");
                tv_Result.setText("กรุณารูดใบขับขี่\nหันแถบดำเข้าเครื่อง");
                tv_Result.setText("\nชื่อ...........................................................");
                mCardReadManager = mDriverManager.getCardReadManager();
                MagCard magCard = mCardReadManager.getMAGCard();
                magCard.magCardOpen();
                final CardInfoEntity cardInfoEntity = magCard.getMagReadData();

                searchMagnetCard();

                bt_CancelDrivingLicence.setText("บัตรประชาชน");
            }
        });

        /*==================================================*/
        /*================= bt_CancelDrivingLicence=============================*/
        bt_CancelDrivingLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_CancelDrivingLicence.setVisibility(View.GONE);
                tv_Result.setText("");
                mCardReadManager = mDriverManager.getCardReadManager();
                MagCard magCard = mCardReadManager.getMAGCard();
                magCard.magCardClose();

            }
        });
        /*==================================================*/
    }////------ protected void onCreate(Bundle savedInstanceState) {
        /*==================================================*/

    private void printBitmap(Bitmap bitmap) {
        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus != SdkResult.SDK_PRN_STATUS_PAPEROUT) {
            mPrinter.setPrintAppendBitmap(bitmap, Layout.Alignment.ALIGN_CENTER);
            printStatus = mPrinter.setPrintStart();
        }
    }

    private void printBitmap_PicID_thai(Bitmap bitmap) {
        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus != SdkResult.SDK_PRN_STATUS_PAPEROUT) {

            mPrinter.setPrintAppendBitmap(bitmap,Layout.Alignment.ALIGN_CENTER);

            printStatus = mPrinter.setPrintStart();
        }
    }

    //----------------------------------ditherImage---------------------------------------/
    private static <init> Bitmap getResizedBitmap(Bitmap var0, int var1, int var2) {
        Bitmap var10000 = var0;
        int var10001 = var2;
        int var10002 = var1;
        Bitmap var10003 = var0;
        int var5 = var0.getWidth();
        var1 = var10003.getHeight();
        float var6 = (float)var10002 / (float)var5;
        float var3 = (float)var10001 / (float)var1;
        Matrix var4 = new Matrix();
        Matrix var7;
        var7 = var4;
       // var4.<init>();
        var7.setScale(var6, var3);
        return Bitmap.createBitmap(var10000, 0, 0, var5, var1, var4, true);
    }
    private static byte[] changeToGrayScale3(byte[] var0, int var1, int var2) {
        int var10000 = var1;
        byte[] var14 = new byte[(var1 = var0.length) * 9];
        var10000 *= 3;
        int var3 = var10000 * 4;
        int var4 = 0;
        int var5 = var10000 * 2 * 4;
        int var6 = 0;
        int var9 = var5;
        int var8 = var3;

        for(int var7 = var3; var6 <= var1 - 4; var6 += 4) {
            double var10002 = (double)(var0[var6] & 255) * 0.3;
            double var10 = (double)(var0[var6 + 1] & 255) * 0.59;
            double var12 = (double)(var0[var6 + 2] & 255) * 0.11;
            var10 = var10002 + var10 + var12;
            if (var4 >= var7) {
                var10000 = var4 = var7 + var5;
                var7 += var3 * 3;
                var9 = (var8 = var10000 + var3) + var3;
            }

            if (var10 < 11.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = 0;
                var14[var4 + 9] = 0;
                var14[var4 + 10] = 0;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = 0;
                var14[var8 + 5] = 0;
                var14[var8 + 6] = 0;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = 0;
                var14[var8 + 9] = 0;
                var14[var8 + 10] = 0;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = 0;
                var14[var9 + 5] = 0;
                var14[var9 + 6] = 0;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = 0;
                var14[var9 + 9] = 0;
                var14[var9 + 10] = 0;
                var14[var9 + 11] = -1;
            } else if (var10 >= 11.0 && var10 < 21.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = 0;
                var14[var4 + 9] = 0;
                var14[var4 + 10] = 0;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = 0;
                var14[var8 + 5] = 0;
                var14[var8 + 6] = 0;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = 0;
                var14[var8 + 9] = 0;
                var14[var8 + 10] = 0;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = 0;
                var14[var9 + 5] = 0;
                var14[var9 + 6] = 0;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 21.0 && var10 < 32.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = 0;
                var14[var4 + 9] = 0;
                var14[var4 + 10] = 0;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = 0;
                var14[var8 + 5] = 0;
                var14[var8 + 6] = 0;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = 0;
                var14[var9 + 5] = 0;
                var14[var9 + 6] = 0;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 32.0 && var10 < 64.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = 0;
                var14[var8 + 5] = 0;
                var14[var8 + 6] = 0;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = 0;
                var14[var9 + 5] = 0;
                var14[var9 + 6] = 0;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 64.0 && var10 < 96.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = 0;
                var14[var4 + 9] = 0;
                var14[var4 + 10] = 0;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 96.0 && var10 < 128.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = 0;
                var14[var4 + 5] = 0;
                var14[var4 + 6] = 0;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 128.0 && var10 < 160.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = -1;
                var14[var4 + 5] = -1;
                var14[var4 + 6] = -1;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = 0;
                var14[var9 + 1] = 0;
                var14[var9 + 2] = 0;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 160.0 && var10 < 192.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = -1;
                var14[var4 + 5] = -1;
                var14[var4 + 6] = -1;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = 0;
                var14[var8 + 1] = 0;
                var14[var8 + 2] = 0;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = -1;
                var14[var9 + 1] = -1;
                var14[var9 + 2] = -1;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 192.0 && var10 < 214.0) {
                var14[var4 + 0] = 0;
                var14[var4 + 1] = 0;
                var14[var4 + 2] = 0;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = -1;
                var14[var4 + 5] = -1;
                var14[var4 + 6] = -1;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = -1;
                var14[var8 + 1] = -1;
                var14[var8 + 2] = -1;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = -1;
                var14[var9 + 1] = -1;
                var14[var9 + 2] = -1;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            } else if (var10 >= 214.0) {
                var14[var4 + 0] = -1;
                var14[var4 + 1] = -1;
                var14[var4 + 2] = -1;
                var14[var4 + 3] = -1;
                var14[var4 + 4] = -1;
                var14[var4 + 5] = -1;
                var14[var4 + 6] = -1;
                var14[var4 + 7] = -1;
                var14[var4 + 8] = -1;
                var14[var4 + 9] = -1;
                var14[var4 + 10] = -1;
                var14[var4 + 11] = -1;
                var14[var8 + 0] = -1;
                var14[var8 + 1] = -1;
                var14[var8 + 2] = -1;
                var14[var8 + 3] = -1;
                var14[var8 + 4] = -1;
                var14[var8 + 5] = -1;
                var14[var8 + 6] = -1;
                var14[var8 + 7] = -1;
                var14[var8 + 8] = -1;
                var14[var8 + 9] = -1;
                var14[var8 + 10] = -1;
                var14[var8 + 11] = -1;
                var14[var9 + 0] = -1;
                var14[var9 + 1] = -1;
                var14[var9 + 2] = -1;
                var14[var9 + 3] = -1;
                var14[var9 + 4] = -1;
                var14[var9 + 5] = -1;
                var14[var9 + 6] = -1;
                var14[var9 + 7] = -1;
                var14[var9 + 8] = -1;
                var14[var9 + 9] = -1;
                var14[var9 + 10] = -1;
                var14[var9 + 11] = -1;
            }

            var4 += 12;
            var8 += 12;
            var9 += 12;
        }

        return var14;
    }
    private static byte[] changeToGrayScale2(byte[] var0, int var1, int var2) {
        int var10000 = var1;
        byte[] var12 = new byte[(var1 = var0.length) * 4];
        int var3 = var10000 * 2 * 4;
        int var4 = 0;
        int var5 = 0;
        int var7 = var3;

        for(int var6 = var3; var5 <= var1 - 4; var5 += 4) {
            double var10002 = (double)(var0[var5] & 255) * 0.3;
            double var8 = (double)(var0[var5 + 1] & 255) * 0.59;
            double var10 = (double)(var0[var5 + 2] & 255) * 0.11;
            var8 = var10002 + var8 + var10;
            if (var4 >= var6) {
                var4 = var6 + var3;
                var7 = var6 + var3 * 2;
                var6 = var7;
            }

            if (var8 < 32.0) {
                var12[var4 + 0] = 0;
                var12[var4 + 1] = 0;
                var12[var4 + 2] = 0;
                var12[var4 + 3] = -1;
                var12[var4 + 4] = 0;
                var12[var4 + 5] = 0;
                var12[var4 + 6] = 0;
                var12[var4 + 7] = -1;
                var12[var7 + 0] = 0;
                var12[var7 + 1] = 0;
                var12[var7 + 2] = 0;
                var12[var7 + 3] = -1;
                var12[var7 + 4] = 0;
                var12[var7 + 5] = 0;
                var12[var7 + 6] = 0;
                var12[var7 + 7] = -1;
            } else if (var8 >= 32.0 && var8 < 64.0) {
                var12[var4 + 0] = 0;
                var12[var4 + 1] = 0;
                var12[var4 + 2] = 0;
                var12[var4 + 3] = -1;
                var12[var4 + 4] = 0;
                var12[var4 + 5] = 0;
                var12[var4 + 6] = 0;
                var12[var4 + 7] = -1;
                var12[var7 + 0] = 0;
                var12[var7 + 1] = 0;
                var12[var7 + 2] = 0;
                var12[var7 + 3] = -1;
                var12[var7 + 4] = -1;
                var12[var7 + 5] = -1;
                var12[var7 + 6] = -1;
                var12[var7 + 7] = -1;
            } else if (var8 >= 64.0 && var8 < 128.0) {
                var12[var4 + 0] = 0;
                var12[var4 + 1] = 0;
                var12[var4 + 2] = 0;
                var12[var4 + 3] = -1;
                var12[var4 + 4] = -1;
                var12[var4 + 5] = -1;
                var12[var4 + 6] = -1;
                var12[var4 + 7] = -1;
                var12[var7 + 0] = 0;
                var12[var7 + 1] = 0;
                var12[var7 + 2] = 0;
                var12[var7 + 3] = -1;
                var12[var7 + 4] = -1;
                var12[var7 + 5] = -1;
                var12[var7 + 6] = -1;
                var12[var7 + 7] = -1;
            } else if (var8 >= 128.0 && var8 < 192.0) {
                var12[var4 + 0] = 0;
                var12[var4 + 1] = 0;
                var12[var4 + 2] = 0;
                var12[var4 + 3] = -1;
                var12[var4 + 4] = -1;
                var12[var4 + 5] = -1;
                var12[var4 + 6] = -1;
                var12[var4 + 7] = -1;
                var12[var7 + 0] = -1;
                var12[var7 + 1] = -1;
                var12[var7 + 2] = -1;
                var12[var7 + 3] = -1;
                var12[var7 + 4] = -1;
                var12[var7 + 5] = -1;
                var12[var7 + 6] = -1;
                var12[var7 + 7] = -1;
            } else if (var8 >= 192.0) {
                var12[var4 + 0] = -1;
                var12[var4 + 1] = -1;
                var12[var4 + 2] = -1;
                var12[var4 + 3] = -1;
                var12[var4 + 4] = -1;
                var12[var4 + 5] = -1;
                var12[var4 + 6] = -1;
                var12[var4 + 7] = -1;
                var12[var7 + 0] = -1;
                var12[var7 + 1] = -1;
                var12[var7 + 2] = -1;
                var12[var7 + 3] = -1;
                var12[var7 + 4] = -1;
                var12[var7 + 5] = -1;
                var12[var7 + 6] = -1;
                var12[var7 + 7] = -1;
            }

            var4 += 8;
            var7 += 8;
        }

        return var12;
    }
    public static Bitmap Dither(Bitmap var0, int var1, int var2, int var3) {
        Bitmap var10000;
        int var4;
        if (var3 == 3) {
            var10000 = var0;
            var4 = var1 / 3;
            var0 = getResizedBitmap(var10000, var4, var2 / 3);
        } else {
            if (var3 != 2) {
                return getResizedBitmap(var0, var1, var2);
            }

            var10000 = var0;
            var4 = var1 / 2;
            var0 = getResizedBitmap(var10000, var4, var2 / 2);
        }

        int var7 = var3;
        var1 = var0.getWidth();
        var2 = var0.getHeight();
        ByteBuffer var6;
        ByteBuffer var10001 = var6 = ByteBuffer.allocate(var0.getRowBytes() * var0.getHeight());
        var0.copyPixelsToBuffer(var6);
        var0.recycle();
        byte[] var5 = var10001.array();
        if (var7 == 3) {
            var5 = changeToGrayScale3(var5, var1, var2);
            var10000 = Bitmap.createBitmap(var1 * 3, var2 * 3, Bitmap.Config.ARGB_8888);
        } else {
            var5 = changeToGrayScale2(var5, var1, var2);
            var10000 = Bitmap.createBitmap(var1 * 2, var2 * 2, Bitmap.Config.ARGB_8888);
        }

        var10000.copyPixelsFromBuffer(ByteBuffer.wrap(var5));
        return var10000;
    }
    public Bitmap ditherImage(Bitmap var1, int var2, int var3, int var4)  {

            if (var2 <= 3000 && var3 <= 3000) {
                if (var4 <= var2 && var4 <= var3) {
                    return Dither(var1, var2, var3, var4);
                } else {
                     return null;
                }
            } else {
                return null;
            }

    }
    private void personPhoto(Bitmap bitmap) {
        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus != SdkResult.SDK_PRN_STATUS_PAPEROUT) {
            mPrinter.setPrintAppendBitmap(ditherImage(bitmap, 184, 200, 3),Layout.Alignment.ALIGN_CENTER);
            printStatus = mPrinter.setPrintStart();
        }
    }
//*****************************************************************************************
    private void printText(String shareFact) {
        //String TV_Show2 = TV_Show;
        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {
            //out of paper
            Toast.makeText(MainActivity.this, "Out of paper", Toast.LENGTH_SHORT).show();
        } else {

            PrnStrFormat format = new PrnStrFormat();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat  df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            SimpleDateFormat  TT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String formattTime = TT.format(c);

            InputStream inputStream;
            InputStream inputStream_SCANQR;
            try {
                inputStream = MainActivity.this.getAssets().open("Logo_PRN_new01.bmp");
                inputStream_SCANQR = MainActivity.this.getAssets().open("QRCode_STATE-INDUSTRY.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            printBitmap(bitmap);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setTextSize(40);
            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString("KSB" +
                    "", format);
            format.setTextSize(40);
            format.setStyle(PrnTextStyle.BOLD);

            format.setTextSize(40);

            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setStyle(PrnTextStyle.BOLD);
            format.setTextSize(30);
            mPrinter.setPrintAppendString("ใบอนุญาติผ่าน เข้า-ออก บจก.", format);
            mPrinter.setPrintAppendString("Enter Pass Permission Form", format);
            //format.setFont(PrnTextFont.CUSTOM);
            //format.setPath(Environment.getExternalStorageDirectory() + "/fonts/simsun.ttf");
            format.setTextSize(35);
            format.setFont(PrnTextFont.SANS_SERIF);
            mPrinter.setPrintAppendString("เอกสารผู้มาติดต่อ", format);
            format.setStyle(PrnTextStyle.NORMAL);
            mPrinter.setPrintAppendString(formattedDate, format);
            mPrinter.setPrintAppendString("เวลาเข้า :"+formattTime, format);
//            ICCard icCard = mCardReadManager.getICCard();
         //   Bitmap bitmap_photo_thaiId = getPhoto(icCard);
            //printBitmap(bitmap2);

            if(CheckPhoto!=0){
                Drawable drawable5 = imageView_iccard.getDrawable();
                Bitmap bitmap2 = ((BitmapDrawable) drawable5).getBitmap();
                personPhoto(bitmap2);
            }else{
                format.setStyle(PrnTextStyle.BOLD);
                format.setTextSize(30);
                mPrinter.setPrintAppendString("  ", format);
                format.setFont(PrnTextFont.SANS_SERIF);
                format.setTextSize(35);
                mPrinter.setPrintAppendString("เอกสารผู้มาติดต่อ", format);
            }

            tv_Result.toString();
            format.setTextSize(25);
            format.setStyle(PrnTextStyle.NORMAL);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
          //  mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(shareFact, format);

           // format.setAli(Layout.Alignment.ALIGN_CENTER);
            //format.setTextSize(50);
            //format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString("       ", format);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
            format.setStyle(PrnTextStyle.NORMAL);
            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString(" ประเภทรถ (Type Car.)", format);
            format.setStyle(PrnTextStyle.NORMAL);
            mPrinter.setPrintAppendString(" ( )รถยนต์/Car", format);
            mPrinter.setPrintAppendString(" ( )รถจักรยานยนต์/Motorcycle", format);
            mPrinter.setPrintAppendString(" ( )อื่นๆ/Other...........................", format);
            format.setTextSize(25);
            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString(" ทะเบียนรถ(ID Car.)", format);
            mPrinter.setPrintAppendString(" ..................................................", format);
            mPrinter.setPrintAppendString(" ..................................................", format);
           // mPrinter.setPrintAppendString(" ยี่ห้อรถ(Model)...................................", format);

            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString(" จุดประสงค์ (Objective)", format);

            format.setStyle(PrnTextStyle.NORMAL);
            mPrinter.setPrintAppendString(" ( )ดูห้องโครงการ", format);
          //  mPrinter.setPrintAppendString(" ( )บริษัท แม่บ้านทำงานห้อง..................", format);
           // mPrinter.setPrintAppendString(" ( )บริษัท ช่างแอร์ทำงานห้อง..................", format);
            mPrinter.setPrintAppendString(" ( )ส่งของ/Delivery  ( )รับของ/Receiver", format);
            mPrinter.setPrintAppendString(" ( )วางบิล/Billing      ( )เก็บของ/Preserve", format);
            mPrinter.setPrintAppendString(" ( )ส่งอาหาร/Food delivery", format);
            mPrinter.setPrintAppendString(" ( )ธุระส่วนตัว/Personal Business", format);
            mPrinter.setPrintAppendString(" ( )ติดต่องาน/Contact", format);
            mPrinter.setPrintAppendString(" ( )สัมภาษณ์งาน/Interview ", format);
            mPrinter.setPrintAppendString(" ( )อื่นๆ/Other..................................", format);
            mPrinter.setPrintAppendString(" ........................................................", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString("บริษัทฯ/หน่วยงานของผู้ที่มาติดต่อ", format);
            mPrinter.setPrintAppendString("(Visitor's Company/Institue)", format);
            mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString("ต้องการพบ นาย/นาง/น.ส.", format);
            mPrinter.setPrintAppendString("(Who want to meet Mr.,Mrs.,Ms.)", format);
            mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString("...................................................", format);
            // mPrinter.setPrintAppendString("เลขที่บ้าน/เลขที่ห้อง", format);
            //mPrinter.setPrintAppendString("(House or Room Number)", format);
            //mPrinter.setPrintAppendString("...................................................", format);
            //mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString("เบอร์โทรผู้มาติดต่อ(Tell)", format);
            mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString("...................................................", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setTextSize(30);
            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString("ลงชื่อ......................................... ", format);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString("ผู้มาติดต่อกรุณาเซ็นต์ชื่อ ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);

            mPrinter.setPrintAppendString("ลงชื่อ.......................................... ", format);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString("ผู้ให้ติดต่อ ", format);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setTextSize(26);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" -----------------------------", format);
            mPrinter.setPrintAppendString("รปภ.บันทึกเวลาออก", format);
            format.setStyle(PrnTextStyle.BOLD);
            format.setTextSize(26);
            mPrinter.setPrintAppendString("ข้อปฏิบัติ ", format);
            mPrinter.setPrintAppendString(" ( Company Gate-Pass Regulation )", format);


            Drawable drawable2 = Drawable.createFromStream(inputStream_SCANQR, null);
            Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();
            printBitmap(bitmap2);

            format.setTextSize(17);
            mPrinter.setPrintAppendString("ผู้เข้าพบปฎิบัติตามนโยบาย ้อปฏิบัติ ( Company Gate-Pass Regulation ) เเละยินยอมเปิดเผยข้อมูลส่วนบุคคลให้เเก่ บจก.รักษาความปลอดภัย พี.อาร์.เอ็น เเละเจ้าของสถานที่เท่านั้น " +
                    "เพื่อจุดประสงค์ ส่งของ/ส่งอาหาร/พัสดุ หรือ ขอเข้าพื้นที่ ทาง บจก.รักษาความปลอดภัยฯ " + "ไม่เปิดเผยข้อมูลของผู้เข้าพบเเก่บุคคลอื่นนอกจากนี้หากผู้เข้าพบไม่ได้กระทำความเสียหายเเก่ ผู้ให้พบหรือเจ้าของสถานที่ ", format);

            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);


            printStatus = mPrinter.setPrintStart();

        }

    }
    private void initSdk() {
        int status = mSys.sdkInit();
        if(status != SdkResult.SDK_OK) {
            mSys.sysPowerOn();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        status = mSys.sdkInit();
        if(status != SdkResult.SDK_OK) {
            //init failed.
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            if (actionBar != null) {
                actionBar.setTitle(getString(R.string.demo_title));
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public Bitmap getPhoto(ICCard icCard) {
        int blockNumber = 1, blockLength, index = 0;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int PERSONAL_PIC_LENGTH = 5118;
        byte[] pictureBuffer;

        while (index < PERSONAL_PIC_LENGTH) {
            blockLength = ((PERSONAL_PIC_LENGTH - index) > 0xff) ? 0xff:(PERSONAL_PIC_LENGTH - index);

            byte[] data = getPhotoByte(blockNumber);
            byte[] dota_photo   = icCard.icExchangeAPDU(CardSlotNoEnum.SDK_ICC_USERCARD,data);

            buffer.write(dota_photo, 0, blockLength);

            index += blockLength;
            blockNumber++;
        }

        pictureBuffer = buffer.toByteArray();
        while (index >= 0 && pictureBuffer[index - 1] == 0x20) index--;

        return BitmapFactory.decodeByteArray(pictureBuffer, 0, index);
    }
    public byte[] getPhotoByte(int index) {
        byte[][] data = {APDU_PHOTO_PART01
                ,APDU_PHOTO_PART02
                ,APDU_PHOTO_PART03
                ,APDU_PHOTO_PART04
                ,APDU_PHOTO_PART05
                ,APDU_PHOTO_PART06
                ,APDU_PHOTO_PART07
                ,APDU_PHOTO_PART08
                ,APDU_PHOTO_PART09
                ,APDU_PHOTO_PART10
                ,APDU_PHOTO_PART11
                ,APDU_PHOTO_PART12
                ,APDU_PHOTO_PART13
                ,APDU_PHOTO_PART14
                ,APDU_PHOTO_PART15
                ,APDU_PHOTO_PART16
                ,APDU_PHOTO_PART17
                ,APDU_PHOTO_PART18
                ,APDU_PHOTO_PART19
                ,APDU_PHOTO_PART20
        };

        byte[] select;

        if (index >= 0 && index < data.length) {
            int find = index-1;
            select = data[find];
        } else {
            select = data[19];
        }

        return select;
    }
    public String convertUTF8(byte[] asciiBytes) {
        String S_CONVERT;
        String output;
        try {
            S_CONVERT = new String(asciiBytes, "TIS620").trim().replaceAll("#", " ");
            output = S_CONVERT.replaceAll("\u0090", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return output;
    }

    public String covert_GENDER(int S_GENDER){

        String  GENDER ="";

        if(S_GENDER == 1){

            GENDER = "ชาย";

        }else if(S_GENDER == 2){
             GENDER = "หญิง";

        }else {
            GENDER = "อื่นๆ";
        }

        return GENDER;
    }

    public static String covertThaiIdDL(String SThaiIdDL){
        ThaiIdDL = SThaiIdDL.toCharArray();
        StringBuilder StringThaiIdDL = new StringBuilder();

        for (int i = 6; i < ThaiIdDL.length; i++) {
            StringThaiIdDL.append(ThaiIdDL[i]);
        }

        return StringThaiIdDL.toString();
    }
    public static String CheckSpaceSurname (String inputString){
        char[] inputChar = inputString.toCharArray();
        StringBuilder surname = new StringBuilder();

        for (int i = 0; i < inputChar.length; i++) {

            if (!Character.isWhitespace(inputChar[i])) {
                surname.append(inputChar[i]);
            }else {
                i =inputChar.length;
            }
        }

        return surname.toString();
    }
    public static String replaceString (String originalString){
        //String originalString = "This#is#a#test#string";
        String replacedString = originalString.replace("^", "")
                .replace("$", " ")
                .replace("^^", "");
        return replacedString;
    }

    public static String swapString (String originalString){
        String[] parts = originalString.trim().split(" ");
        if (parts.length == 3) {
            String lastName = parts[0];
            String firstName = parts[1];
            String title = parts[2];
            
            String output = title + " " + firstName + " " + lastName;
            
            return output;
        } else {
            return originalString;
        }
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    //-----------------------------------2023-05-16----------------------------



    public static String callUploadLog(
            String id_thai,
            String name_thai,
            String name_eng,
            String address,
            String sex,
            String hbd,
            Integer typecard,
            String local_code ,
            Bitmap image

    ) {
        String apiUrl = "https://prn-vsm.com/api/savelog2";
       // String apiUrl = "http://prn-vsm.com/api/savelog2";            เส้นใหม่  line messaging api
        //String apiUrl = "http://vms-prn.com/api/savelog";            เส้นเก่า  line notify

        APICallTask apiCallTask = new APICallTask(new MainActivity(), image, typecard);
        apiCallTask.execute(apiUrl, id_thai, name_thai, name_eng, address, sex, hbd, local_code);
        return "ok";
    }

}
