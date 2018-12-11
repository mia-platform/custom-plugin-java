package eu.miaplatform.crud.dataadapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
  // https://tools.ietf.org/html/rfc3339#section-5.6
  private static String iso8601Format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static Date fromIso8601(String dateS) throws ParseException {
    DateFormat simpleDateFormat = new SimpleDateFormat(iso8601Format);

    return simpleDateFormat.parse(dateS);
  }

  public static String toIso8601(Date date) {
    DateFormat simpleDateFormat = new SimpleDateFormat(iso8601Format);

    return simpleDateFormat.format(date);
  }
}
