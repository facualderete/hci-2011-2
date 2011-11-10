package kwik.app.activities;

import java.util.HashMap;

import kwik.app.R;
import kwik.services.KwikAPIService;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductsActivity extends ListActivity implements OnItemClickListener {
	
	private String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		Intent localIntent = this.getIntent();
		
		
		/* Asociamos la vista del search list con la activity */
		setContentView(R.layout.categories_list);
		
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				KwikAPIService.class);
		
		
		
		
		ListView vi = getListView();
		
		vi.setOnItemClickListener(this);
		startService(intent);
	}
	

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ListView vi = (ListView) arg0;
		@SuppressWarnings("unchecked")
		HashMap<String,Object> map = (HashMap<String,Object>) vi.getItemAtPosition(position);
		
		Integer id = (Integer) map.get("id");

		Intent intent = new Intent(arg1.getContext(), CategoriesActivity.class);
		intent.putExtra("category_id", id);
		startActivity(intent);
	}
}
