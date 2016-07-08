#include <headers\LocalDateWrapper.h>

RewireRuntimeComponent::LocalDate::LocalDate():mLocalDate(){

}

RewireRuntimeComponent::LocalDate::LocalDate(LocalDate^ date):mLocalDate(date){

}

RewireRuntimeComponent::LocalDate::LocalDate(int64 millis):mLocalDate(millis){

}

RewireRuntimeComponent::LocalDate::LocalDate(Platform::String^ dateStr):mLocalDate(dateStr){

}

RewireRuntimeComponent::LocalDate::LocalDate(bool isValid):mLocalDate(isValid){

}

RewireRuntimeComponent::LocalDate::LocalDate(int16 day,int16 month,int16 year):mLocalDate(day,month,year){

}

