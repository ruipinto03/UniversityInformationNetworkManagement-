package API;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Serializable, Comparable<Date> {

  private Integer day;

  private Integer month;

  private Integer year;

  
  public Boolean beforeDate(Date d) {
      if (this.year<d.year){
          return true;
      }else if(this.month<d.month){
          return true;
      }else if (this.month==d.month){
          return this.day<d.day;
      }
  return false;
  }

  public int daysMonth(int month, int year) {
      switch(month){
          case 11:
          case 9:
          case 6:
          case 4:return 30;
          case 2:return (isLeapYear(year)?29:28);
          default: return 31;
      }
  }

  public Boolean afterDate(Date d) {
      if (this.year>d.year){
          return true;
      }else if(this.month>d.month){
          return true;
      }else if (this.month==d.month){
          return this.day>d.day;
      }
  return false;
  }

  public void incrementDate() {
      // Se não for o ultimo dia
      if(this.day!=this.daysMonth(this.month, this.year)){
          this.setDay((this.day)+1);
      }else{
         // Caso seja o ultimo dia do ano
         if(this.day==31 && this.month==12){
             this.setYear(this.year+1);
             this.setDay(1);
             this.setMonth(1);
         }else{
             //Caso seja o ultimo dia de um mes (excepto dezembro)
             this.setMonth(this.month+1);
             this.setDay(1);
         }
      }
  }

  public int differenceYears(Date d) {
      return Math.abs(this.year-d.year);
  }

  public Date(Integer day, Integer month, Integer year) {
      this.day=day;
      this.month=month;
      this.year=year;
  }
  
  public Date(){
      Calendar gregCalendar= new GregorianCalendar();
      this.day=gregCalendar.get(Calendar.DAY_OF_MONTH);
      this.month=gregCalendar.get(Calendar.MONTH);
      this.year=gregCalendar.get(Calendar.YEAR);
  }

  public Boolean isLeapYear() {
      return this.isLeapYear(this.year);
  }

  public Boolean isLeapYear(Integer year) {
      return((year%4==0) && (year%100!=0) || (year%400==0));
  }


    /**
     * @return the day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param day the day to set
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }
    
    @Override
    public String toString(){
        return this.day+"/"+this.month+"/"+this.year;
    }

    public int compareTo(Date d) {
        if (this.year == d.year && this.month == d.month && this.day == d.day) {
            return 0;
        } else if (this.year == d.year) {
            if (this.month == d.month) {
                return this.day < d.day ? -1 : 1;
            } else {
                return this.month < d.month ? -1 : 1;
            }
        } else {
            return this.year < d.year ? -1 : 1;
        }
    }

}