package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.mstt.qa.servicevirtualization.xml.utils.MySaxParser;

public class VirtualizeActionListerner implements ActionListener {

	private final JTextArea requestTextArea;
	private Map<String, String> xmldetails;
	private final JComboBox<String> xpathList;
	private final JPanel dynamicPanel;

	public VirtualizeActionListerner(final JPanel dynamicPanel,
			final JTextArea requestTextArea, final JComboBox<String> xpathList,
			final Map<String, String> xmldetails) {
		this.requestTextArea = requestTextArea;
		this.xmldetails = xmldetails;
		this.xpathList = xpathList;
		this.dynamicPanel = dynamicPanel;
	}

	public void actionPerformed(final ActionEvent paramActionEvent) {
		try {
			removeAllPanel();
			xmldetails = MySaxParser.getElementsFromString(requestTextArea
					.getText());
			System.out.println(xmldetails);
			xpathList.removeAllItems();
			Set<String> xpathSet = xmldetails.keySet();
			for (String xpath : xpathSet) {
				xpathList.addItem(xpath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeAllPanel() {
		for (int i = 1; i < dynamicPanel.getComponentCount(); i++) {
			dynamicPanel.remove(i);
		}
		dynamicPanel.validate();
		dynamicPanel.repaint();
	}
}
