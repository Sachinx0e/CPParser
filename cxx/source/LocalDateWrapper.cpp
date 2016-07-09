#include <headers\LocalDateWrapper.h>

RewireRuntimeComponent::LocalDate::LocalDate():mLocalDate(){

}

RewireRuntimeComponent::LocalDate::LocalDate(LocalDate^ date):mLocalDate(date){

}

RewireRuntimeComponent::LocalDate::LocalDate(int64 millis):mLocalDate(millis){

}

RewireRuntimeComponent::LocalDate::LocalDate(Platform::String^ dateStr):mLocalDate(dateStr){

}

RewireRuntimeComponent::LocalDate::LocalDate(int16 day,int16 month,int16 year):mLocalDate(day,month,year){

}

bool RewireRuntimeComponent::LocalDate::operator!=(LocalDate^ rDate){
   bool returnValue = mLocalDate.operator!=(rDate);
   bool convertedValue = returnValue;
   return convertedValue;
}

bool RewireRuntimeComponent::LocalDate::operator>(LocalDate^ rDate){
   bool returnValue = mLocalDate.operator>(rDate);
   bool convertedValue = returnValue;
   return convertedValue;
}

bool RewireRuntimeComponent::LocalDate::operator<(LocalDate^ rDate){
   bool returnValue = mLocalDate.operator<(rDate);
   bool convertedValue = returnValue;
   return convertedValue;
}

bool RewireRuntimeComponent::LocalDate::operator>=(LocalDate^ rDate){
   bool returnValue = mLocalDate.operator>=(rDate);
   bool convertedValue = returnValue;
   return convertedValue;
}

bool RewireRuntimeComponent::LocalDate::operator(LocalDate^ rDate){
   bool returnValue = mLocalDate.operator(rDate);
   bool convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getDayOfWeek(){
   int returnValue = mLocalDate.getDayOfWeek();
   int convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getWeekOfMonth(){
   int returnValue = mLocalDate.getWeekOfMonth();
   int convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getDayOfMonth(){
   int returnValue = mLocalDate.getDayOfMonth();
   int convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getDayOfYear(){
   int returnValue = mLocalDate.getDayOfYear();
   int convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getMonth(){
   int returnValue = mLocalDate.getMonth();
   int convertedValue = returnValue;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getYear(){
   int returnValue = mLocalDate.getYear();
   int convertedValue = returnValue;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::addYears(int32 num_of_years){
   LocalDate returnValue = mLocalDate.addYears(num_of_years);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::minusYears(int32 num_of_years){
   LocalDate returnValue = mLocalDate.minusYears(num_of_years);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::addMonths(int32 num_of_months){
   LocalDate returnValue = mLocalDate.addMonths(num_of_months);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::minusMonths(int32 num_of_months){
   LocalDate returnValue = mLocalDate.minusMonths(num_of_months);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::addDays(int32 num_of_days){
   LocalDate returnValue = mLocalDate.addDays(num_of_days);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::minusDays(int32 num_of_days){
   LocalDate returnValue = mLocalDate.minusDays(num_of_days);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::addWeeks(int32 num_of_weeks){
   LocalDate returnValue = mLocalDate.addWeeks(num_of_weeks);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::minusWeeks(int32 num_of_weeks){
   LocalDate returnValue = mLocalDate.minusWeeks(num_of_weeks);
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getFirstDateOfYear(){
   LocalDate returnValue = mLocalDate.getFirstDateOfYear();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getLastDateOfYear(){
   LocalDate returnValue = mLocalDate.getLastDateOfYear();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getFirstDateOfMonth(){
   LocalDate returnValue = mLocalDate.getFirstDateOfMonth();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getLastDateOfMonth(){
   LocalDate returnValue = mLocalDate.getLastDateOfMonth();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

int32 RewireRuntimeComponent::LocalDate::getWeekNumber(){
   int returnValue = mLocalDate.getWeekNumber();
   int convertedValue = returnValue;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getFirstDateOfWeek(){
   LocalDate returnValue = mLocalDate.getFirstDateOfWeek();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

LocalDate^ RewireRuntimeComponent::LocalDate::getLastDateOfWeek(){
   LocalDate returnValue = mLocalDate.getLastDateOfWeek();
   RewireRuntimeComponent::LocalDate^ convertedValue = new RewireRuntimeComponent::LocalDate(returnValue);;
   return convertedValue;
}

bool RewireRuntimeComponent::LocalDate::isValid(){
   bool returnValue = mLocalDate.isValid();
   bool convertedValue = returnValue;
   return convertedValue;
}

Platform::String^ RewireRuntimeComponent::LocalDate::toString(){
   std::string returnValue = mLocalDate.toString();
   Platform::String^ convertedValue = string_utils::to_platform_string(returnValue);;
   return convertedValue;
}

RewireRuntimeComponent::LocalDate::LocalDate(Applib::DateTime::LocalDate localdate){
   mLocalDate = localdate;
}