package gui;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ContextMenu {
	String content = "";
	
	public ContextMenu() {
		
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String str) {
		content = str;
	}
	
	public boolean isEmpty() {
		return content.isEmpty();
	}
}
