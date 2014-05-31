package com.example.enoticeapp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
//import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class PostNotice extends Activity {
	private String imagepath=null;
	private ProgressDialog dialog = null;
	private int serverResponseCode = 0;
	private String mFileName=null;
	private String upLoadServerUri = null;
	EditText title,description;
	String title1,description1;
	String fName=null;
	String filepath=null;
	ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        upLoadServerUri = "http://davinder.in/UploadToServer.php";

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_main, menu);
        return true;
    }
    
    @Override
    public boolean  onOptionsItemSelected(MenuItem mItem){
    	int mId = mItem.getItemId();
    	switch(mId){
    		case R.id.gallery:
    			Intent myIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    			startActivityForResult(myIntent,2);
    			return true;
    		case R.id.photo:
    			Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    			if (camIntent.resolveActivity(getPackageManager())!=null)  {
    				try {
    					File imageFile = getImageFile();
    					Uri imgUri = Uri.fromFile(imageFile);
    					Log.d("Image uri of captured",""+imgUri);
    					//	camIntent.putExtra("data", imgUri);
    					camIntent.putExtra("data", imgUri);
    					startActivityForResult(camIntent, 3);
    				}
    				catch (IOException e) {
    					Log.d("Chleya","Niiii");
    					e.printStackTrace();
    				}
        		
    			}
    			return true;
    		default:
    			return true;
    		}
    }

	
	private File getImageFile() throws IOException {
		File imageFile = null;
		
		File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (externalDir != null) {
			String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			fileName += "_IMG";
			Log.d("myTag", externalDir.getAbsolutePath()+"/"+fileName);
			imageFile = File.createTempFile(fileName, ".png", externalDir);
			Log.d("myTAg", imageFile.getAbsolutePath());
			this.mFileName = ""+imageFile.getAbsolutePath();
			Log.d("Get File Uri",""+mFileName);
		}
		
		return imageFile;
	}
    
	@Override
    public void onActivityResult(int requestID, int resultID, Intent i){
    	super.onActivityResult(requestID,resultID,i);
    	if(requestID==2 && resultID==Activity.RESULT_OK){
    		Uri selectedImage=i.getData();
    		imagepath=getPath(selectedImage);
    		File f = new File(imagepath);
    		fName=f.getName();
    		image=(ImageView)findViewById(R.id.imageView1);
    		image.setImageURI(selectedImage);
            image.setVisibility(View.VISIBLE);
            
    	}
    	if(requestID==3 &&resultID==this.RESULT_OK){
    		Log.d("hllo","hello");
    		Bundle extras = i.getExtras();
        	Bitmap mBitmap = (Bitmap)extras.get("data");
    		image = (ImageView)findViewById(R.id.imageView1);// new ImageView(this);
        	image.setImageBitmap(mBitmap);
        	image.setVisibility(View.VISIBLE);
        	saveToFile(mFileName,mBitmap);
        	image.setId(R.id.imageView1);
        	startScanner();
        	imagepath=mFileName;
        	
        }
    }
    private void startScanner() {
    	Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    	File f = new File(mFileName);
    	Uri fileUri = Uri.fromFile(f);
    	fName=fileUri.getLastPathSegment();
    	Log.d("Simple FileName",fName);
    	Log.d("link for gallery",""+fileUri);
    	scanIntent.setData(fileUri);
    	this.sendBroadcast(scanIntent);
    }
    
    public void saveToFile(String filename,Bitmap bmp) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            bmp.compress(CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch(Exception e) {}
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onPost(View view){
	  
	  dialog = ProgressDialog.show(PostNotice.this, "", "Uploading data...", true);
	  dialog.setCancelable(true);
	  
	  title=(EditText)findViewById(R.id.title);
	  description=(EditText)findViewById(R.id.description);
	  title1=title.getText().toString();
	  description1=description.getText().toString();
		//fName=s
		 new Thread(new Runnable() {
          public void run() {
        	//  if(title1.length()>0 && description1.length()>0 && fName.length()>0)
             uploadData(title1,description1,fName);     
        	  
        	  Log.d("upload image path:",""+imagepath);  
        	  if(imagepath!=null)
        		  uploadFile(imagepath);
        	  dialog.dismiss(); 
                                       
          }
        }).start();  
		 
  }
  
    public void uploadData(String title, String description, String filename){
    	Log.d("Hello","hello");
    	String url = "http://davinder.in/insertData.php";
    	
    	filepath="http://davinder.in/uploads/";
    	if(filename!=null)
    		filepath+=filename;
    	else
    		filepath="";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description",description));
        params.add(new BasicNameValuePair("filepath",filepath));
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {//
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        try {//
        	Log.d("APAchEEE","Running MySQL query");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.d("response",""+httpResponse);
			 //if(filepath==null)
		        	
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
       
        	
    }
  public int uploadFile(String sourceFileUri) {
      
	  
	  String fileName = sourceFileUri;

      HttpURLConnection conn = null;
      DataOutputStream dos = null;  
      String lineEnd = "\r\n";
      String twoHyphens = "--";
      String boundary = "*****";
      int bytesRead, bytesAvailable, bufferSize;
      byte[] buffer;
      int maxBufferSize = 1 * 1024 * 1024; 
      File sourceFile = new File(sourceFileUri); 
      
      if (!sourceFile.isFile()) {
    	  
           dialog.dismiss(); 
           
           Log.e("uploadFile", "Source File not exist :"+imagepath);
           
           runOnUiThread(new Runnable() {
               public void run() {
            	  // messageText.setText("Source File not exist :"+ imagepath);
            	   Toast.makeText(PostNotice.this, "Source File does not exist", Toast.LENGTH_SHORT).show();
               }
           }); 
           
           return 0;
       
      }
      else
      {
           try { 
        	   
            	 // open a URL connection to the Server
               FileInputStream fileInputStream = new FileInputStream(sourceFile);
               URL url = new URL(upLoadServerUri);
               
               // Open a HTTP  connection to  the URL
               conn = (HttpURLConnection) url.openConnection(); 
               conn.setDoInput(true); // Allow Inputs
               conn.setDoOutput(true); // Allow Outputs
               conn.setUseCaches(false); // Don't use a Cached Copy
               conn.setRequestMethod("POST");
               conn.setRequestProperty("Connection", "Keep-Alive");
               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
               conn.setRequestProperty("uploaded_file", fileName); 
               
               dos = new DataOutputStream(conn.getOutputStream());
     
               dos.writeBytes(twoHyphens + boundary + lineEnd); 
               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
            		                     + fileName + "\"" + lineEnd);
               
               dos.writeBytes(lineEnd);
     
               // create a buffer of  maximum size
               bytesAvailable = fileInputStream.available(); 
     
               bufferSize = Math.min(bytesAvailable, maxBufferSize);
               buffer = new byte[bufferSize];
     
               // read file and write it into form...
               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                 
               while (bytesRead > 0) {
            	   
                 dos.write(buffer, 0, bufferSize);
                 bytesAvailable = fileInputStream.available();
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                 
                }
     
               // send multipart form data necesssary after file data...
               dos.writeBytes(lineEnd);
               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
     
               // Responses from the server (code and message)
               serverResponseCode = conn.getResponseCode();
               String serverResponseMessage = conn.getResponseMessage();
                
               Log.i("uploadFile", "HTTP Response is : " 
            		   + serverResponseMessage + ": " + serverResponseCode);
               
               if(serverResponseCode == 200){
            	   
                   runOnUiThread(new Runnable() {
                        public void run() {
                        	String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                  		          +"/var/www/uploads";
                        	//messageText.setText(msg);
                            Toast.makeText(PostNotice.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });                
               }    
               
               //close the streams //
               fileInputStream.close();
               dos.flush();
               dos.close();
                
          } catch (MalformedURLException ex) {
        	  
              dialog.dismiss();  
              ex.printStackTrace();
              
              runOnUiThread(new Runnable() {
                  public void run() {
                	  //messageText.setText("MalformedURLException Exception : check script url.");
                      Toast.makeText(PostNotice.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                  }
              });
              
              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
          } catch (Exception e) {
        	  
              dialog.dismiss();  
              e.printStackTrace();
              
              runOnUiThread(new Runnable() {
                  public void run() {
                	  //messageText.setText("Got Exception : see logcat ");
                      Toast.makeText(PostNotice.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                  }
              });
              Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  
          }
         // dialog.dismiss();       
          return serverResponseCode; 
          
       } // End else block 
     }

}