package com.bonc.Flow;

import java.util.List;
/**
 * 
 * @author zhangruo 20160923
 * 
 */
public class ProductInfo {
	private String productId;//产品ID
	private String optType;//00：订购
	private List<PackageElement> packageElement;//产品下附加包及包元素（资费，服务）
	private String enableTag;//生效方式： 1立即生效  2次月生效

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public List<PackageElement> getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(List<PackageElement> packageElement) {
		this.packageElement = packageElement;
	}

	public String getEnableTag() {
		return enableTag;
	}

	public void setEnableTag(String enableTag) {
		this.enableTag = enableTag;
	}

}
