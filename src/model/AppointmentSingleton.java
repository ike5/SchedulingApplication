package model;

public final class AppointmentSingleton {
    private Appointment appointment;

    private final static AppointmentSingleton INSTANCE = new AppointmentSingleton();

    private AppointmentSingleton() {
    }

    public static AppointmentSingleton getInstance() {
        return INSTANCE;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }
}
