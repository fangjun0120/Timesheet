package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DAY_SHEET")
public class DaySheet implements Serializable {

    private static final long serialVersionUID = 897800754266574883L;

    @Id
    @GeneratedValue
    @Column(name = "DAY_SHEET_ID", unique = true, nullable = false)
    private Long daySheetId;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "HOUR", nullable = false)
    private int hour;

    @ManyToOne
    @JoinColumn(name="WEEK_SHEET_ID")
    private WeekSheet weekSheet;

    public DaySheet() {
    }

    public DaySheet(Date date, int hour) {
        this.date = date;
        this.hour = hour;
    }

    public Long getDaySheetId() {
        return daySheetId;
    }

    public void setDaySheetId(Long daySheetId) {
        this.daySheetId = daySheetId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public WeekSheet getWeekSheet() {
        return weekSheet;
    }

    public void setWeekSheet(WeekSheet weekSheet) {
        this.weekSheet = weekSheet;
    }

    @Override
    public String toString() {
        return "DaySheet [daySheetId=" + daySheetId + ", date=" + date
                + ", hour=" + hour + ", weekSheet=" + weekSheet.getWeekSheetId() + "]";
    }

}
