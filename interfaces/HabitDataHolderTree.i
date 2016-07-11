HEADER_FILE := habits\HabitDataHolderTree.h

CONSTRUCTOR_IGNORE := HabitDataHolderTree(std::map<int,int> categoryMap, CategoryDataHolder* categoryDataHolder, std::vector<HabitDataHolder*>* listOfHabitDataHolders);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\HabitWrapper.h
IMPORT_HEADER := headers\CategoryWrapper.h