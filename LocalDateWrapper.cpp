#include <LocalDateWrapper.h>

Applib::DateTime::LocalDate::LocalDate():mLocalDate(){

}

Applib::DateTime::LocalDate::LocalDate(LocalDate^ date):mLocalDate(date){

}

Applib::DateTime::LocalDate::LocalDate(LocalDate^ date):mLocalDate(date){

}

Applib::DateTime::LocalDate::LocalDate(int64 millis):mLocalDate(millis){

}

Applib::DateTime::LocalDate::LocalDate(Platform::String^ dateStr):mLocalDate(dateStr){

}

Applib::DateTime::LocalDate::LocalDate(bool isValid):mLocalDate(isValid){

}

Applib::DateTime::LocalDate::LocalDate(int16 day,int16 month,int16 year):mLocalDate(day,month,year){

}

