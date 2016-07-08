#pragma once

#include <LocalDate.h>
#include <headers\DurationWrapper.h>

namespace RewireRuntimeComponent {
   public ref class LocalDate sealed {
      public:
         LocalDate();

         LocalDate(LocalDate^ date);

         LocalDate(int64 millis);

         LocalDate(Platform::String^ dateStr);

         LocalDate(int16 day,int16 month,int16 year);

         int32 getDayOfWeek();

         int32 getWeekOfMonth();

         int32 getDayOfMonth();

         int32 getDayOfYear();

         int32 getMonth();

         int32 getYear();

         LocalDate^ addYears(int32 num_of_years);

         LocalDate^ minusYears(int32 num_of_years);

         LocalDate^ addMonths(int32 num_of_months);

         LocalDate^ minusMonths(int32 num_of_months);

         LocalDate^ addDays(int32 num_of_days);

         LocalDate^ minusDays(int32 num_of_days);

         LocalDate^ addWeeks(int32 num_of_weeks);

         LocalDate^ minusWeeks(int32 num_of_weeks);

         LocalDate^ getFirstDateOfYear();

         LocalDate^ getLastDateOfYear();

         LocalDate^ getFirstDateOfMonth();

         LocalDate^ getLastDateOfMonth();

         int32 getWeekNumber();

         LocalDate^ getFirstDateOfWeek();

         LocalDate^ getLastDateOfWeek();

         bool isValid();

         Platform::String^ toString();

      private:
         Applib::DateTime::LocalDate mLocalDate;

   };
}
