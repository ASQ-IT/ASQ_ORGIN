package asq.pos.bnpl.tabby.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraDisAmtObj;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyItemsObj {
    private String title;
    private String description;
    private Number quantity;
    private String unit_price;
    private int reference_id;
    private String image_url;
    private String product_url;
    private String gender;
    private String category;
    private String color;
    private String product_material;
    private String size_type;
    private String size;
    private String brand;
    private String itemId;
    
    private AsqBnplTabbyAmountObj total_amount;
    private AsqBnplTabbyDisAmtObj discount_amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public int getReference_id() {
        return reference_id;
    }

    public void setReference_id(int reference_id) {
        this.reference_id = reference_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProduct_material() {
        return product_material;
    }

    public void setProduct_material(String product_material) {
        this.product_material = product_material;
    }

    public String getSize_type() {
        return size_type;
    }

    public void setSize_type(String size_type) {
        this.size_type = size_type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setTotal_amount(AsqBnplTabbyAmountObj total_amount) {
		this.total_amount = total_amount;
	}

	public AsqBnplTabbyAmountObj getTotal_amount() {
		return total_amount;
	}

	public AsqBnplTabbyDisAmtObj getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(AsqBnplTabbyDisAmtObj discount_amount) {
		this.discount_amount = discount_amount;
	}

}
