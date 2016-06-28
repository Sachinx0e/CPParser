#pragma once

#include <string>
#include <DurationDate.h>


namespace Applib {


	namespace DateTime {
		class LocalDate {
		public:

			/**
			* Default constructor, date is intialized to today
			*/
            LocalDate();

            /**
            * Destructor
            */
            ~LocalDate();

			/**
			*  Copy Constructor
			*/
            LocalDate(const LocalDate& date);

			/**
			* Copy constuctor , copy from pointer
			*/
            LocalDate(LocalDate* date);

            //copy assignment
            LocalDate& operator= (const LocalDate& rDate);

			/**
			*  Construct LocalDate from millis
			**/
            LocalDate(long millis);

			/**
			*  Construct LocalDate from string
			*  String format : yyyymmdd
			**/
            LocalDate(std::string dateStr);


            /**
            * Construct valid or invalid date time
            */
            LocalDate(bool isValid);

			/*
			* Construct date from day, month, year
			*/
            LocalDate(short day, short month, short year);

			/**
			* Overide == operator
			*/
            bool operator==(const LocalDate& rDate);

            bool operator==(const LocalDate& rDate) const;


			/**
			* Overide != operator
			*/
            bool operator!=(LocalDate& rDate);

            bool operator!=(const LocalDate& rDate) const;


			/**
			* Overide > operator
			*/
            bool operator>(LocalDate& rDate);
			

            bool operator>(const LocalDate& rDate) const;

			/**
			* Overide < operator
			*/
            bool operator<(LocalDate& rDate);

            bool operator<(const LocalDate& rDate) const;


			/**
			* Overide >= operator
			*/
            bool operator>=(LocalDate& rDate);

            bool operator>=(const LocalDate& rDate) const;


			/**
			* Overide <= operator
			*/
            bool operator <=(LocalDate& rDate);


            bool operator <=(const LocalDate& rDate) const;


			/**
			*  Day Of Week
			*  @returns day num starting from 0
			*/
            int getDayOfWeek() ;

            /**
             * @brief Get Week Of Month
             * @return week of month starting from 1
             */
            int getWeekOfMonth();

			/**
			*  Day Of Month
			*  @return day num starting from 1
			*/
            int getDayOfMonth();

			/**
			*  Day Of Year
			*  @return day num starting from 1
			*/
            int getDayOfYear();

			/**
			*  Month
			*  @return month num starting from 1
			*/
            int getMonth();

            int getMonth() const;

			/**
			*  Year
			*/
            int getYear();

            int getYear() const;

			/**
			*  Add Year
			*/
            LocalDate addYears(int num_of_years);

			/**
			*  Minus Year
			*/
            LocalDate minusYears(int num_of_years);

			/**
			*  Add Month
			*/
            LocalDate addMonths(int num_of_months);

			/**
			*  Minus Month
			*/
            LocalDate minusMonths(int num_of_months);

			/**
			*  Add Days
			*/
            LocalDate addDays(int num_of_days);

			/**
			*  Minus Days
			*/
            LocalDate minusDays(int num_of_days);

			/**
			*  Plus Weeks
			*/
            LocalDate addWeeks(int num_of_weeks);

			/**
			*  Minus Weeks
			*/
            LocalDate minusWeeks(int num_of_weeks);

			/**
			*	Get difference in terms of days
			*/
            long differenceBetween(const LocalDate& date);

			/**
			*	Get first date of year
			*/
            LocalDate getFirstDateOfYear();


			/**
			*	Get last date of year
			*/
            LocalDate getLastDateOfYear();

			/**
			*	Get first date of month
			*/
            LocalDate getFirstDateOfMonth();

			/**
			*	Get last date of month
			*/
            LocalDate getLastDateOfMonth();

			/**
			*  Get the week number
			*/
            int getWeekNumber();

			/**
			*  Get first date of week
			*/
            LocalDate getFirstDateOfWeek();

			/**
			* Get the last date of week
			*/
            LocalDate getLastDateOfWeek();

            /**
             * @brief Check if the date is valid
             * @return Return true if date is valid, false otherwise
             */
            bool isValid();

			/**
			*  Convert to string
			*  Format returned : ddmmyyyy 
			*/
            std::string toString();


			/**
			*  Get the difference between two dates as duration
			*/
            static DurationDate getDifference(LocalDate* lDate, LocalDate* rDate);

		private:
            class impl;
            impl* pimpl;
		};

	}
}

