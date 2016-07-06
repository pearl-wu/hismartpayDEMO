package com.hismart.pay;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;
import org.apache.cordova.*;
import org.json.JSONException;


public class MainActivity extends CordovaPlugin {
    	
    	  protected static final String LOG_TAG = "hismartTV__pay";

    	  public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException{
    		      			
    		if(action.equals("Pay")){
    			
    			if(!checkPackageInfo(cordova.getActivity(),"com.hisense.hiphone.payment")){
    				Intent it = new Intent(Intent.ACTION_VIEW);
    				it.setData(Uri.parse("himarket://details?id="+ "com.hisense.hiphone.payment"+"&isAutoDownload="+ 1));
    				cordova.getActivity().startActivity(it);
    				return false;
    			}

    			Intent intent = new Intent();
    			intent.setAction("com.hisense.hiphone.payment.MAIN");
    			
    			//应用名称
    			intent.putExtra("appName", "聚好看"); 
    			
    			//包名
    			intent.putExtra("packageName", "cn.com.ebais.kyytvhismart");
    			 
    			//将自己的应用包名联通海信分配的md5key一同进行md5签名，md5的签名方法在demo里有可直接使用
    			//String sign = MD5Signature.md5("cn.com.ebais.kyytvhismart"+"612F7F6BF73CA1EB5A66DA0BDB7367AC");
    			String sign = "cn.com.ebais.kyytvhismart"+"612F7F6BF73CA1EB5A66DA0BDB7367AC";
    			
    			intent.putExtra("paymentMD5Key", sign);
    			
    			intent.putExtra("tradeNum", "1021248651654");
    			
    			//价格一定要转成String
    			intent.putExtra("goodsPrice", "0.01");
    			
    			intent.putExtra("goodsName", "小马过河");
    			
    			intent.putExtra("goodsDesc", "小马过河");
    			
    			//为空，默认支付到海信
    			intent.putExtra("alipayUserAmount", "");
    			
    			//这里填商户自己的回调地址
    			intent.putExtra("notifyUrl","http://10.0.64.107:9080/notify");	
    			
    			//表示支持支付宝和银联，不支持微信和 短代
    			intent.putExtra("platformId","1,3");
    			
    			try {
    				
    				(cordova.getActivity()).startActivityForResult(intent, 1);
    				
    			} catch (ActivityNotFoundException e) {
    				
	    			Log.d(LOG_TAG , "出现异常版本过低，进入市场升级");
	    			e.printStackTrace();
	    			Intent it = new Intent(Intent.ACTION_VIEW);
	    			it.setData(Uri.parse("himarket://details?id="+"com.hisense.hiphone.payment"+"&isAutoDownload="+ 1));
	    			cordova.getActivity().startActivity(it);
	    			
    			}	
    			
    	       return true;
    		}else if(action.equals("Iandroid")){
    			boolean tr = args.getBoolean(0);
    			if(tr){
    			  String androidId = Secure.getString(cordova.getActivity().getContentResolver(), Secure.ANDROID_ID);
    			  Resultecho(true, androidId, callbackContext);
    			}
    		  return true;
    		}else if(action.equals("Packageinfo")){
    			int no = args.getInt(0);
    			String packageinfo="";
    			if(no==1){
    				//packageName
    				packageinfo = cordova.getActivity().getPackageName();				    				
    			}else if(no==2){
    				//strVersionCode
    		        try {
    		            PackageInfo packageInfo = cordova.getActivity().getPackageManager().getPackageInfo(cordova.getActivity().getPackageName(),0);
    		            packageinfo = String.valueOf(packageInfo.versionCode);
    		        } catch (NameNotFoundException e) {
    		            // TODO Auto-generated catch block
    		            e.printStackTrace();
    		        }  				
    			}else if(no==3){
    		        try {
    		        	PackageInfo packageInfo = cordova.getActivity().getPackageManager().getPackageInfo(cordova.getActivity().getPackageName(),0);
    		        	packageinfo = packageInfo.versionName;
    		        } catch (NameNotFoundException e) {
    		            // TODO Auto-generated catch block
    		            e.printStackTrace();
    		            packageinfo = "Cannot load Version!";
    		        }  	  				
    			}	
    			Resultecho(true, packageinfo, callbackContext);
    			return true;
    		}else if(action.equals("Echo")){
    			String context = args.getString(0);
    			int duration = args.getInt(1);
    			
    			Toast.makeText(cordova.getActivity(), context, duration).show(); 
		
    			/*Toast mToast = new Toast(cordova.getActivity()); 
    			mToast.setGravity(Gravity.TOP, 0, 0);
    			LayoutInflater inflater = cordova.getActivity().getLayoutInflater();
    			View view = inflater.inflate(R.layout.toast, null);
    			TextView tvMesssage = (TextView) view.findViewById(R.id.textview);
    			mToast.setView(view);
    			mToast.setDuration(Toast.LENGTH_LONG);
    			tvMessage.setText("test"+mToast.getDuration());
    			mToast.show();*/
    		
    			return true;
    		}else if(action.equals("Sign")){   	
    			return true;
    		}    		
    		
    	     return false;
    	  
    	  }       	  


			public void Resultecho(Boolean boo, String meg, CallbackContext callbackContext){
    		   if(boo){
    	           callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, meg));
    		   } else {
    		       callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, meg));
    		   }			
    		}
			
			
			
			public static boolean checkPackageInfo(Context mContext,String packageName) {
				 if (packageName == null || "".equals(packageName))
					 return false;
				 try {
					 ApplicationInfo app = mContext.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
					 if (app != null) {
						 return true;
					 } else {
						 return false;
					 }
				 } catch (NameNotFoundException e) {
					 return false;
				 }
			}

			/*public void onActivityResult(int requestCode, int resultCode, Intent data) {
					super.onActivityResult(requestCode, resultCode, data);
					if(resultCode==RESULT_OK){
					payResult = data.getStringExtra("payResult");//支付结果
					Log.d("test", "payResult is "+payResult);
					platformId =data.getStringExtra("platformId");//支付方式
					Log.d("test", "platformId is "+platformId);
					trade_no =data.getStringExtra("tradeNum");//外部订单号
					 Log.d("test", "tradeNum is "+trade_no);
					}
			}*/


    }
