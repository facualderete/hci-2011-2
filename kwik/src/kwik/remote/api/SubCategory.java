package kwik.remote.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwik.remote.api.exceptions.APIBadResponseException;
import kwik.remote.api.exceptions.HTTPException;
import kwik.remote.api.exceptions.XMLParseException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class SubCategory extends AbstractCategory {
	@Attribute
	int id;
	
	@Element
	int category_id;
	
	@Element
	String code;
	
	@Element
	String name;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Product> getProducts(int language_id, String order, int items_per_page, int page) throws APIBadResponseException, XMLParseException, HTTPException {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("method", "GetSubcategoryList");
		headers.put("language_id", Integer.toString(language_id));
		headers.put("category_id", Integer.toString(this.category_id));
		headers.put("subcategory_id", Integer.toString(this.id));

		headers.put("order", order);
		
		if (items_per_page != AbstractCategory.NO_PARAM && page != AbstractCategory.NO_PARAM) {
			headers.put("items_per_page", Integer.toString(items_per_page));
			headers.put("page", Integer.toString(page));			
		}
		
		Response r = Response.get(Response.COMMON, headers);
		return r.products;
	}
	
	@Override
	public List<SubCategory> getSubCategoryList(int language_id) throws APIBadResponseException, XMLParseException,
			HTTPException {
		return null; // A subcategory doesn't have any subcategories dude.
	}
}