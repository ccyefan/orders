package com.bonc.Flow;
public class PackageElement {
	private String packageId;/* 包编号 */
	private String elementId;/* 元素编号 */
	private String elementType;/* 元素类型 D资费,S服务,A活动，X S服务 */

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

}
