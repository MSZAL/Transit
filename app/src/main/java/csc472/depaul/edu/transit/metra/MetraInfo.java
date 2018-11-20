package csc472.depaul.edu.transit.metra;

import android.os.Parcel;
import android.os.Parcelable;

public final class MetraInfo implements Parcelable {

    private String line = null;
    private String departure = null;
    private String destination = null;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MetraInfo createFromParcel(Parcel in) {
            return new MetraInfo(in);
        }

        public MetraInfo[] newArray(int size) {
            return new MetraInfo[size];
        }
    };

    public MetraInfo(final String line, final String departure, final String destination) {
        this.line = line;
        this.departure = departure;
        this.destination = destination;
    }

    public final String getLine() {
        return line;
    }

    public final String getDeparture() {
        return departure;
    }

    public final String getDestination() {
        return destination;
    }


    public MetraInfo(Parcel in) {
        this.line = in.readString();
        this.departure = in.readString();
        this.destination = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.line);
        dest.writeString(this.departure);
        dest.writeString(this.destination);
    }
}
