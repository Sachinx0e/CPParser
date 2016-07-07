#pragma once


#include <algorithm>
#include <LocalDate.h>
#include <LocalTime.h>
#include <Item.h>
#include <application/DBContract.h>
#include <TextUtils.h>
#include <units/Unit.h>
#include <categories/Category.h>


namespace Rewire {
	class Habit : public Applib::Items::Item {
	public:

		static const int SCHEDULE_FIXED;
		static const int SCHEDULE_FLEXIBLE;
		static const int SCHEDULE_REPEATING;

		static const int PERIOD_WEEK;
		static const int PERIOD_MONTH;
		static const int PERIOD_YEAR;

		Habit(const std::string& name);

		//Name
		void setName(const std::string& name);

		std::string getName();

		//Description
		void setDescription(const std::string& description);

		std::string getDescription();

		//Current Streak
		void setCurrentStreak(int currentStreak);

		int getCurrentStreak();


		//Longest Streak
		void setLongestStreak(int longestStreak);

		int getLongestStreak();

		//Order Num
		void setOrderNum(int orderNum);

		int getOrderNum();

		//Active Days
		void setActiveDays(const std::vector<int>& active_days);

		std::vector<int> getActiveDays();

        bool getIsDayActive(int dayOfWeek);

		//CategoryID
		void setCategoryID(const std::string& categoryID);

		std::string getCategoryID();


		//Schedule
		void setSchedule(int schedule);

		int getSchedule();

		//Flexible schedule data
		void setFlexibleScheduleData(int period, int numofDays);

		int getPeriod();

		int getNumOfDays();

		//Repeating Days
		void setRepeatingDays(int repeatingDays);
		
		int getRepeatingDays();

		//Archived
		void setIsArchived(bool isArchived);
		
		bool getIsArchived();

		//UNIT ID
		void setUnitID(const std::string& unitID);

		std::string getUnitID();

		//Target count
        void setTargetCount(double targetCount);

        double getTargetCount();
		

		//Target Count Time
		void setTargetCountTime(const Applib::DateTime::LocalTime& targetTime);

		Applib::DateTime::LocalTime getTargetCountTime();
		
		//Start Date
		void setStartDate(const Applib::DateTime::LocalDate& startDate);

		Applib::DateTime::LocalDate getStartDate();

		misc::ContentValues* getValues();

		/**
		*   Function to get a vector 
		*/
		static std::vector<int> createActiveDays(const std::vector<int>& days_to_exclude);

        bool operator == (const Habit& item);

        std::string toString();

        bool getIsNumerical();

	private:

		std::string mName;
		std::string mDescription;
		int mCurrentStreak;
		int mLongestStreak;
		int mOrderNum;
        std::vector<int> mActive_days = {0,1,2,3,4,5,6};
		std::string mCategoryID;
		int mSchedule;
		bool mIsArchived;
		int mPeriod;
		int mNumOfDays;
		int mRepeatingDays;
		std::string mUnitID;
        double mTargetCount;
		Applib::DateTime::LocalTime mTargetTime;
		Applib::DateTime::LocalDate mStartDate;
		
		void updateState() {
			if (mPeriod == PERIOD_WEEK) {
				if (mNumOfDays > 7 || mNumOfDays < 0) {
					mNumOfDays = 7;
				}
			}
			else if (mPeriod == PERIOD_MONTH) {
				if (mNumOfDays > 30 || mNumOfDays < 0) {
					mNumOfDays = 30;
				}
			}
			else if (mPeriod == PERIOD_YEAR) {
				if (mNumOfDays > 365 || mNumOfDays < 0) {
					mNumOfDays = 365;
				}
			}
		}

	};
}


