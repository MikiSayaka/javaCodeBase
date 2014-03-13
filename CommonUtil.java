import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CommonUtil implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * If input string is null or empty string, then return the empty string.
	 * @param	String str Input string
	 * @return String rtn
	 * @author Miki Sayaka 2013.01.15
	 * */
	public static String isNotNull(String str) {
		return (str == null || str.length() == 0) ? "" : str.trim();
	}

	/**
	 * If input string is null or empty string, then return the string that want to replace.
	 * @param	String str Input string
	 * @param	String rpl The string will replace the input string when the input string is null or empty string.
	 * @return String rtn
	 * @author Miki Sayaka 2013.01.15
	 * */
	public static String isNotNull(String str, String rpl) {
		return (str == null || str.length() == 0) ? rpl.trim() : str.trim();
	}

	/**
	 * If the HashMap is null, then return true, or return false.
	 * @param	Map htm	Input HashMap
	 * @return boolean 
	 * @author Miki Sayaka 2013.01.15
	 */
	@SuppressWarnings("rawtypes")
	public static boolean hmIsNull(Map htm) {
		return (htm == null || htm.size() == 0) ? true : false;
	}

	/**
	 * If the List is null, then return true, or return false.
	 * @param	List lst Input List
	 * @param	boolean	rtn
	 * @author Miki Sayaka 2013.01.15
	 * */
	@SuppressWarnings("rawtypes")
  public static boolean ltIsNull(List lt) {
	  return (lt == null || lt.size() == 0) ? true : false;
	}

  /**
   * If the string array is null, then return true, or return false
   * @param String[] str Input string array
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean StrIsNull(String[] str) {
    return (str == null || str.length == 0) ? true : false;
  }

  /**
   * If the string is empty, then return true, or return false.
   * @param String str Input string
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean isEmpty(String s) {
    return (s == null || s.length() == 0) ? true : false;
  }

  /**
   * Translate the input traditional Chinese character to unicode.
   * @param String s Input string
   * @return String The string that has been translated.
   * @author YCC 2010.8.13
   */
  public static String toUnicodeStr(String s) {
    StringBuffer sb = new StringBuffer();
    if (isEmpty(s)) return "";
    for (int i = 0; i < s.length(); i++) {
      sb.append("\\u");
      sb.append(Integer.toHexString((int)s.charAt(i)));
    }
    return sb.toString();
  }

  /**
   * Translate the input traditional Chinese character to web unicode.
   * @param String s Input string
   * @return String The string that has been translated.
   * @author YCC 2010.8.13
   */
  public static String toWebUnicodeStr(String s) {
    StringBuffer sb = new StringBuffer();
    if (isEmpty(s)) return "";
    for (int i = 0; i < s.length(); i++) {
      sb.append("&#");
      sb.append((int)s.charAt(i));
      sb.append(";");
    }
    return sb.toString();
  }

  /**
   * Translate the input web unicode to traditional Chinese character.
   * @param String s Input string
   * @return String The string that has been translated.
   * @author YCC 2010.8.13
   */
  public static String webUnicodeToString(String s) {

    if (isEmpty(s)) return "";
    StringBuffer sb = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, ";", true);
    try {
      boolean charEnd = false;
      while(st.hasMoreTokens()) {
        String ch = st.nextToken();
        int c = -1;
        try {
          if (ch.equals(";")) {
            if (charEnd) {
              charEnd = false;
              continue;
            } else {
              throw new Exception("onlyFlag");
            }
          }

          int pos = ch.indexOf("&#");
          if (pos != -1) {
            sb.append(ch.substring(0, pos));
            ch = ch.substring(pos);
          } else {
            throw new Exception("onlyEnglishString");
          }

          charEnd = true;
          ch = ch.replaceAll("&#", "");
          c = Integer.parseInt(ch);
          sb.append((char)c);
        } catch (Exception e) {
          sb.append(ch);
        }
      }
    } catch (Exception ex) {
      //  TODO  Do something when the exception occur.
    }
    return sb.toString();
  }

  /**
   * Replace the < and > to &lt; and &gt;
   * @param String str Input string
   * @return String str
   * @author Miki Sayaka 2013.07.11
   */
  public static String replChar(String str) {
    if (str != null) {
      str = str.replaceAll("<", "&lt;");
      str = str.replaceAll(">", "&gt;");
    } else {
      return "";
    }
    return str;
  }

  /**
   * If the input string is integer, return true, or return false.
   * @param String strNumber Input string
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean isNumber(String strNumber) {

    boolean isSuccess = true;
    try {
      new Long(strNumber);
    } catch (NumberFormatException nfe) {
      isSuccess = false;
    } catch (Exception e) {
      isSuccess = false;
    }
    return isSuccess;
  }

  /**
   * If the input string is double, return true, or return false.
   * @param String strNumber Input string
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean isDouble(String strNumber) {

    boolean isSuccess = true;
    try {
      new Double(strNumber);
    } catch (NumberFormatException nfe) {
      isSuccess = false;
    } catch (Exception e) {
      isSuccess = false;
    }
    return isSuccess;
  }

  /**
   * Get the average and round it up to the nearest integer.
   * @param String val Input string array
   * @param int arg Which number to round up
   * @author Miki Sayaka 2013.07.11
   */
  public static String getRound(String[] val, int arg) {

    if (StrIsNull(val) || arg < 1) return "";
    double total = 0;
    String result = "";
    try {
      for (int i = 0; i < val.length; i++) {
        total += Double.parseDouble(val[i]);
      }
      result = new BigDecimal(total / val.length).setScale(arg, BigDecimal.ROUND_HALF_EVEN).toString();
    } catch (Exception e) {
      return "";
    }
    return result;
  }

  /**
   * Sweep all HTML tag
   * @param String str Input string
   * @return String str
   * @author Miki Sayaka 2013.07.11
   */
  public static String clearTag(String str) {

    if (str != null) {
      str = str.replaceAll("<html>", "");
      str = str.replaceAll("<head>", "");
      str = str.replaceAll("<body>", "");
      str = str.replaceAll("</body>", "");
      str = str.replaceAll("</head>", "");
      str = str.replaceAll("</html>", "");
    } else {
      return "";
    }
    return str;
  }

  /**
   * Replace the [br], [b] or [/b] to HTML tag.
   * @param String str Input string
   * @return String
   * @author Miki Sayaka 2013.07.11
   */
  public static String replaceUsrTag(String str) {

    if (str != null) {
      str = str.replaceAll("\\[br\\]", "<br/>");
      str = str.replaceAll("\\[b\\]", "<b>");
      str = str.replaceAll("\\[/b\\]", "</b>");
    } else {
      return "";
    }
    return str;
  }

  /**
   * Format the number to money
   * @param String inputStr Input string
   * @return String
   * @author Miki Sayaka 2013.07.11
   */
  public static String setStrToCurrency(String inputStr) {

    if (isEmpty(inputStr)) return "0";
    if (!isNumber(inputStr)) return "0";

    NumberFormat nf = NumberFormat.getNumberInstance();
    return nf.format(Integer.parseInt(inputStr));
  }

  /**
   * Confirm the R.O.C's resident number , if the resident is legal then return true, or return false
   * @param String residentNo Resident number
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean checkResidentNo(String residentNo) {
    
    boolean flag = true;
    try {
      residentNo = isNotNull(residentNo);
      if (residentNo.length() == 10) {
        residentNo = residentNo.toUpperCase();
        String regHeadLetter = residentNo.substring(0, 1);
        String reg2ndLetter = residentNo.substring(1, 2);
        String pattern = "[A-Z]";
        String headLetter = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        int sum = 0;
        if (regHeadLetter.matches(pattern) && !reg2ndLetter.matches(pattern)) {
          residentNo = residentNo.substring(1);
          if (!isNumber(residentNo)) {
            return false;
          }
          residentNo = (headLetter.indexOf(regHeadLetter) + 10) + "" + residentNo;
        } else if (regHeadLetter.matches(pattern) && reg2ndLetter.matches(pattern)) {
          residentNo = residentNo.substring(2);
          if (!isNumber(residentNo)) {
            return false;
          }
          residentNo = (headLetter.indexOf(regHeadLetter) + 10) + "" + ((headLetter.indexOf(reg2ndLetter) + 10) % 10) + "" + residentNo;
        } else {
          return false;
        }
        sum = Integer.parseInt(residentNo.substring(0, 1)) + 
              (Integer.parseInt(residentNo.substring(1, 2)) * 9) + 
              (Integer.parseInt(residentNo.substring(2, 3)) * 8) + 
              (Integer.parseInt(residentNo.substring(3, 4)) * 7) + 
              (Integer.parseInt(residentNo.substring(4, 5)) * 6) + 
              (Integer.parseInt(residentNo.substring(5, 6)) * 5) + 
              (Integer.parseInt(residentNo.substring(6, 7)) * 4) + 
              (Integer.parseInt(residentNo.substring(7, 8)) * 3) + 
              (Integer.parseInt(residentNo.substring(8, 9)) * 2) + 
              Integer.parseInt(residentNo.substring(9, 10)) + 
              Integer.parseInt(residentNo.substring(10, 11));
        if ((sum % 10) != 0) {
          flag = false;
        }
      } else {
        flag = false;
      }
    } catch (Exception e) {
      flag = false;
    }
    return flag;
  }

  /**
   * Check the cell phone number <br/>
   * [1] Cell phone number can not be empty <br/>
   * [2] Number's length must be 10 <br/>
   * [3] Content must be number <br/>
   * [4] First two number must be 09 <br/>
   * @param cellphone Cell phone number
   * @return boolean
   * @author Miki Sayaka 2013.07.11
   */
  public static boolean checkCellphone(String cellphone) {
    boolean result = false;
    if (!isEmpty(cellphone)) {
      if (cellphone.length() == 10) {
        if (isNumber(cellphone)) {
          if ("09".equals(cellphone.substring(0, 2))) result = true;
        }
      }
    }
    return result;
  }

  /**
   * Check the correct E-Mail address.
   * @param String email Input E-Mail
   * @return boolean result
   * @author Miki Sayaka 2013.07.12
   */
  public static boolean checkEmail(String email) {
    boolean result = true;
    if (!isEmpty(email)) {
      String emailPattern = "^([\\w]+)(([-\\.][\\w]+)?)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
      result = email.matches(emailPattern);
    } else {
      result = false;
    }
    return result;
  }
  
  /**
   * Check the union number
   * @param String unionCode  Union number
   * @return boolean result
   * @author Miki Sayaka 2013.07.12
   */
  public static boolean checkUnionNumber(String unionCode) {
    boolean bolResult = false;
    String weight = "12121241";
    boolean type2 = false;
    if (isNumber(unionCode) || unionCode.length() == 8) {
      int tmp = 0;
      int sum = 0;
      for (int i = 0; i < 8; i++) {
        tmp = (unionCode.charAt(i) - '0') * (weight.charAt(i) - '0');
        sum += (int) (tmp/10) + (tmp%10);
        if (i == 6 && unionCode.charAt(i) == '7') {
          type2 = true; 
        }
      }
      if (type2) {
        if ((sum % 10) == 0 || ((sum + 1) % 10) == 0) {
          bolResult = true;
        }
      } else {
        if ((sum % 10) == 0) {
          bolResult = true;
        }
      }
    }
    return bolResult;
  }
}
