package kwik.services;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.List;

import kwik.remote.api.Category;
import kwik.remote.api.Product;
import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class GetCategoriesService extends IntentService {

	private final String TAG = getClass().getSimpleName();

	public static final String GET_CATEGORIES_CMD = "GetCategories";

	public static final int STATUS_CONNECTION_ERROR = -1;
	public static final int STATUS_ERROR = -2;
	public static final int STATUS_ILLEGAL_ARGUMENT = -3;
	public static final int STATUS_OK = 0;

	/*
	 * Se debe crear un constructor sin parametros y asignarle un nombre al
	 * servicio.
	 */
	public GetCategoriesService() {
		super("GetCategoriesService");
	}

	/*
	 * Evento que se ejecuta cuando se invocó el servicio por medio de un
	 * Intent.
	 */
	@Override
	protected void onHandleIntent(final Intent intent) {
		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		final String command = intent.getStringExtra("command");

		final Bundle b = new Bundle();
		try {
			if (command.equals(GET_CATEGORIES_CMD)) {
				getCategories(receiver, b);
				
				Product p = Product.getProduct(28);
				Log.e("Product", p.image_url);
			} 
		} catch (SocketTimeoutException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_CONNECTION_ERROR, b);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ILLEGAL_ARGUMENT, b);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			receiver.send(STATUS_ERROR, b);
		} catch (APIBadResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HTTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Es importante terminar el servicio lo antes posible.
		this.stopSelf();
	}

	private void getCategories(ResultReceiver receiver, Bundle b) throws ClientProtocolException, IOException, JSONException {
		//final DefaultHttpClient client = new DefaultHttpClient();
		//final HttpResponse response = client.execute(new HttpGet("http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetCategoryList&language_id=1"));
	//	if ( response.getStatusLine().getStatusCode() != 200 ) {
	//		throw new IllegalArgumentException(response.getStatusLine().toString());
	//	}
		//----
		List<Category> l = null;
		try {
			l = Category.getCategoryList(1);
			
			/** Handling XML */
//			SAXParserFactory spf = SAXParserFactory.newInstance();
//			SAXParser sp = spf.newSAXParser();
//			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
//			URL sourceUrl = new URL("http://eiffel.itba.edu.ar/hci/service/Catalog.groovy?method=GetCategoryList&language_id=1");

			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
//			CategoriesXMLHandler categoriesXMLHandler = new CategoriesXMLHandler();
//			xr.setContentHandler(categoriesXMLHandler);
//			xr.parse(new InputSource(sourceUrl.openStream()));

		} catch (Exception e) {
			
			//System.out.println("XML Pasing Excpetion = " + e);
		}
		//return ProductXMLHandler.info;
		//----
		
		
		b.putSerializable("return", (Serializable) l);

		receiver.send(STATUS_OK, b);
	}


}
