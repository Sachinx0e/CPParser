HEADER_FILE := habits\HabitDataHolderTree.h

CONSTRUCTOR_IGNORE := HabitDataHolderTree(std::map<int,int> categoryMap, CategoryDataHolder* categoryDataHolder, std::vector<HabitDataHolder*>* listOfHabitDataHolders);

MEMOWN := Habit* getHabitRef(long categoryPosition, long habitPosition);
MEMOWN := Category* getCategoryRef(long position);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\HabitWrapper.h
IMPORT_HEADER := headers\CategoryWrapper.h