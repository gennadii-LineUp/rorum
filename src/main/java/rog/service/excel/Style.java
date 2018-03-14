package rog.service.excel;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class Style {

//	FONT STYLES *******************************************************
    public static StyleBuilder boldStyle = stl.style().bold();
    public static StyleBuilder italicStyle = stl.style().italic();
    public static StyleBuilder boldItalicStyle = stl.style().boldItalic();
//    *****************************************************************

    //

    public static Integer riskWidth = 500;
    public static Integer measureUnitsWidth = 500;
	public static Integer purposesWidth = 500;
	public static Integer symbolProcesuZSZJ = 300;
	public static Integer reactionOnRisk = 500;
	public static Integer standardWidth = 150;
}
