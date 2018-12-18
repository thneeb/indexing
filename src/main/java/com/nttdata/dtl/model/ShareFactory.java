package com.nttdata.dtl.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ShareFactory {
    private Share KR7098460009 = new Share("KR7098460009", "A1H4BM", "098460.KQ", "KOH YOUNG TECHNOLOGY INC", "KRW");
    private Share KYG014081064 = new Share("KYG014081064", "A1H5YN", "1590.TW", "AIRTAC INTERNATIONAL GROUP", "TWD");
    private Share TW0002049004 = new Share("TW0002049004", "A0RC07", "2049.TW", "HIWIN TECHNOLOGIES", "TWD");
    private Share TW0002308004 = new Share("TW0002308004", "956766", "2308.TW", "DELTA ELECTRONICS INC", "TWD");
    private Share TW0002395001 = new Share("TW0002395001", "542352", "2395.TW", "ADVANTECH CO LTD ORD", "TWD");
    private Share JP3592600005 = new Share("JP3592600005", "858646", "6104.T", "TOSHIBA MACHINE CO LTD", "JPY");
    private Share JP3102400003 = new Share("JP3102400003", "852856", "6118.T", "AIDA ENGINEERING LTD", "JPY");
    private Share JP3809200003 = new Share("JP3809200003", "869134", "6134.T", "FUJI CORP", "JPY");
    private Share JP3651210001 = new Share("JP3651210001", "251734", "6268.T", "NABTESCO CORP ORD", "JPY");
    private Share JP3162600005 = new Share("JP3162600005", "874794", "6273.T", "SMC CORP ORD", "JPY");
    private Share JP3765150002 = new Share("JP3765150002", "912928", "6324.T", "HARMONIC DRIVE SYSTEMS INC", "JPY");
    private Share JP3497400006 = new Share("JP3497400006", "857887", "6383.T", "DAIFUKU CO LTD", "JPY");
    private Share JP3124400007 = new Share("JP3124400007", "862229", "6436.T", "AMANO CORPORATION", "JPY");
    private Share JP3813200007 = new Share("JP3813200007", "859059", "6474.T", "NACHI-FUJIKOSHI CORP ORD", "JPY");
    private Share JP3539250005 = new Share("JP3539250005", "887915", "6481.T", "THK CO LTD ORD", "JPY");
    private Share JP3944500002 = new Share("JP3944500002", "904275", "6482.T", "YUSHIN PRECISION EQUIPMENT", "JPY");
    private Share JP3902400005 = new Share("JP3902400005", "856532", "6503.T", "MITSUBISHI ELECTRIC CORP", "JPY");
    private Share JP3932000007 = new Share("JP3932000007", "857658", "6506.T", "YASKAWA ELECTRIC CORP ORD", "JPY");
    private Share JP3497800007 = new Share("JP3497800007", "859037", "6622.T", "DAIHEN CORP", "JPY");
    private Share JP3197800000 = new Share("JP3197800000", "856877", "6645.T", "OMRON CORP ORD", "JPY");
    private Share JP3955000009 = new Share("JP3955000009", "856912", "6841.T", "YOKOGAWA ELECTRIC CORP ORD", "JPY");
    private Share JP3236200006 = new Share("JP3236200006", "874827", "6861.T", "KEYENCE CORP ORD", "JPY");
    private Share JP3551500006 = new Share("JP3551500006", "858734", "6902.T", "DENSO CORP ORD", "JPY");
    private Share JP3197700002 = new Share("JP3197700002", "930034", "6914.T", "OPTEX GROUP CO LTD", "JPY");
    private Share JP3802400006 = new Share("JP3802400006", "863731", "6954.T", "FANUC CORP", "JPY");
    private Share JP3630400004 = new Share("JP3630400004", "868162", "7732.T", "TOPCON CORP", "JPY");
    private Share CH0012221716 = new Share("CH0012221716", "919730", null, "ABB LTD-REG", "CHF");
    private Share US0527691069 = new Share("US0527691069", "869964", "ADSK", "AUTODESK INC", "USD");
    private Share DE000A111338 = new Share("DE000A111338", "A11133", "AM3D.DE", "SLM SOLUTIONS GROUP AG", "EUR");
    private Share KYG037AX1015 = new Share("KYG037AX1015", "A1J58B", null, "AMBARELLA INC", "USD");
    private Share US0043971052 = new Share("US0043971052", "A0MKWM", "ARAY", "ACCURAY INC", "USD");
    private Share CA0019401052 = new Share("CA0019401052", "897908", "ATA.TO", "ATS AUTOMATION TOOLING SYSTEMS INC", "CAD");
    private Share US0080731088 = new Share("US0080731088", "A0MJX7", "AVAV", "AEROVIRONMENT INC", "USD");
    private Share US1143401024 = new Share("US1143401024", "257275", "BRKS", "BROOKS AUTOMATION INC", "USD");
    private Share DE0005102008 = new Share("DE0005102008", "510200", null, "BASLER AG", "EUR");
    private Share US1273871087 = new Share("US1273871087", "873567", "CDNS", "CADENCE DESIGN SYSTEMS INC", "USD");
    private Share FI0009013429 = new Share("FI0009013429", "A0ERKS", null, "CARGOTEC OYJ CLASS B", "EUR");
    private Share US1924221039 = new Share("US1924221039", "878090", "CGNX", "COGNEX CORP", "USD");
    private Share FR0000130650 = new Share("FR0000130650", "901295", null, "DASSAULT SYSTEMES SA", "EUR");
    private Share US88554D2053 = new Share("US88554D2053", "888346", "DDD", "3D SYSTEMS CORP", "USD");
    private Share US2441991054 = new Share("US2441991054", "850866", "DE", "DEERE & CO", "USD");
    private Share DE0005565204 = new Share("DE0005565204", "556520", null, "DUERR AG", "EUR");
    private Share SE0000163628 = new Share("SE0000163628", "896279", null, "ELEKTA AB", "SEK");
    private Share US3116421021 = new Share("US3116421021", "909382", "FARO", "FARO TECHNOLOGIES INC", "USD");
    private Share US3024451011 = new Share("US3024451011", "917029", "FLIR", "FLIR SYSTEMS INC", "USD");
    private Share DE0006602006 = new Share("DE0006602006", "660200", null, "GEA GROUP AKTIENGESELLSCHAFT", "EUR");
    private Share SE0000103699 = new Share("SE0000103699", "873339", null, "HEXAGON AB CLASS B", "SEK");
    private Share US42330P1075 = new Share("US42330P1075", "A0JD3R", "HLX", "HELIX ENERGY SOLUTIONS GROUP", "USD");
    private Share VGG456671053 = new Share("VGG456671053", "A0X91G", null, "HOLLYSYS AUTOMATION TECHNOLO", "USD");
    private Share US44980X1090 = new Share("US44980X1090", "602224", "IPGP", "IPG PHOTONICS CORP", "USD");
    private Share US4627261005 = new Share("US4627261005", "A0F5CC", "IRBT", "IROBOT CORP", "USD");
    private Share DE0005488100 = new Share("DE0005488100", "548810", null, "ISRA VISION AG", "EUR");
    private Share US46120E6023 = new Share("US46120E6023", "888024", "ISRG", "INTUITIVE SURGICAL INC", "USD");
    private Share US4778391049 = new Share("US4778391049", "A0Q6F9", "JBT", "JOHN BEAN TECHNOLOGIES CORP", "USD");
    private Share DE000A2NB601 = new Share("DE000A2NB601", "A2NB60", null, "JENOPTIK AG", "EUR");
    private Share CH0100837282 = new Share("CH0100837282", "A0RMWK", null, "KARDEX AG-REG", "CHF");
    private Share DE000KGX8881 = new Share("DE000KGX8881", "KGX888", null, "KION GROUP AG", "EUR");
    private Share DE0006335003 = new Share("DE0006335003", "633500", null, "KRONES AG", "EUR");
    private Share US5339001068 = new Share("US5339001068", "908231", "LECO", "LINCOLN ELECTRIC HOLDINGS", "USD");
    private Share US5627501092 = new Share("US5627501092", "913804", "MANH", "MANHATTAN ASSOCIATES INC", "USD");
    private Share CA57778L1031 = new Share("CA57778L1031", "A2GSXQ", "MAXR", "MAXAR TECHNOLOGIES LTD", "CAD");
    private Share US5950171042 = new Share("US5950171042", "886105", "MCHP", "MICROCHIP TECHNOLOGY INC", "USD");
    private Share IL0011068553 = new Share("IL0011068553", "A0YFTA", null, "MAZOR ROBOTICS LTD", "ILS");
    private Share US6365181022 = new Share("US6365181022", "894640", "NATI", "NATIONAL INSTRUMENTS CORP", "USD");
    private Share US6556631025 = new Share("US6556631025", "866725", "NDSN", "NORDSON CORP", "USD");
    private Share CA67000B1040 = new Share("CA67000B1040", "A2AJW7", "NOVT", "NOVANTA INC", "USD");
    private Share US67020Y1001 = new Share("US67020Y1001", "A0HGWX", "NUAN", "NUANCE COMMUNICATIONS INC", "USD");
    private Share US67066G1040 = new Share("US67066G1040", "918422", "NVDA", "NVIDIA CORP", "USD");
    private Share GB00B3MBS747 = new Share("GB00B3MBS747", "A1C2GZ", null, "OCADO GROUP PLC", "GBP");
    private Share US6752321025 = new Share("US6752321025", "865291", "OII", "OCEANEERING INTL INC", "USD");
    private Share US69370C1009 = new Share("US69370C1009", "A1H9GN", "PTC", "PARAMETRIC TECHNOLOGY CORP", "USD");
    private Share US7475251036 = new Share("US7475251036", "883121", "QCOM", "QUALCOMM INC", "USD");
    private Share NL0012169213 = new Share("NL0012169213", "A2DKCH", null, "QIAGEN NV", "USD");
    private Share US7542121089 = new Share("US7542121089", "867419", "RAVN", "RAVEN INDUSTRIES INC", "USD");
    private Share US7739031091 = new Share("US7739031091", "903978", "ROK", "ROCKWELL AUTOMATION INC", "USD");
    private Share GB0007323586 = new Share("GB0007323586", "868884", null, "RENISHAW PLC", "GBP");
    private Share FR0000121972 = new Share("FR0000121972", "860180", null, "SCHNEIDER ELECTRIC SE", "EUR");
    private Share DE0007236101 = new Share("DE0007236101", "723610", null, "SIEMENS AG", "EUR");
    private Share IL0011267213 = new Share("IL0011267213", "A1J5UR", null, "STRATASYS LTD", "USD");
    private Share US8793601050 = new Share("US8793601050", "926932", "TDY", "TELEDYNE TECHNOLOGIES INC", "USD");
    private Share CH0012100191 = new Share("CH0012100191", "922557", null, "TECAN GROUP AG-REG", "CHF");
    private Share US8807701029 = new Share("US8807701029", "859892", "TER", "TERADYNE INC", "USD");
    private Share US8962391004 = new Share("US8962391004", "882295", "TRMB", "TRIMBLE INC", "USD");
    private Share CH0033361673 = new Share("CH0033361673", "A0M2K9", null, "U-BLOX AG", "CHF");
    private Share US92220P1057 = new Share("US92220P1057", "852812", "VAR", "VARIAN MEDICAL SYSTEMS INC", "USD");
    private Share US9839191015 = new Share("US9839191015", "880135", "XLNX", "XILINX INC", "USD");
    private Share US9892071054 = new Share("US9892071054", "882578", "ZBRA", "ZEBRA TECHNOLOGIES CORP", "USD");

    private Share US5949181045 = new Share("US5949181045", "870747", "MSFT", "Microsoft Corporation", "USD");

    public List<Share> listShares() {
        return Arrays.asList(US5949181045, US9892071054, US9839191015, US92220P1057, CH0033361673, US8962391004,
                US8807701029, CH0012100191, US8793601050, IL0011267213, DE0007236101, FR0000121972, GB0007323586,
                US7739031091, US7542121089, NL0012169213, US7475251036, US69370C1009, US6752321025, GB00B3MBS747,
                US67066G1040, US67020Y1001, CA67000B1040, US6556631025, US6365181022, IL0011068553, US5950171042,
                CA57778L1031, US5627501092, US5339001068, DE0006335003, DE000KGX8881, CH0100837282, DE000A2NB601,
                US4778391049, US46120E6023, DE0005488100, US4627261005, US44980X1090, VGG456671053, US42330P1075,
                SE0000103699, DE0006602006, US3024451011, US3116421021, SE0000163628, DE0005565204, US2441991054,
                US88554D2053, FR0000130650, US1924221039, FI0009013429, US1273871087, DE0005102008, US1143401024,
                US0080731088, CA0019401052, US0043971052, KYG037AX1015, DE000A111338, US0527691069, CH0012221716,
                JP3630400004, JP3802400006, JP3197700002, JP3551500006, JP3236200006, JP3955000009, JP3197800000,
                JP3497800007, JP3932000007, JP3902400005, JP3944500002, JP3539250005, JP3813200007, JP3124400007,
                JP3497400006, JP3765150002, JP3162600005, JP3651210001, JP3809200003, JP3102400003, JP3592600005,
                TW0002395001, TW0002308004, TW0002049004, KYG014081064, KR7098460009);
    }
}