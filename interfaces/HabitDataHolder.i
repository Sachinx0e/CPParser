HEADER_FILE := habits\HabitDataHolder.h
PARENT_FILE := DataHolder.h := true

CONSTRUCTOR_IGNORE := HabitDataHolder(std::vector<Habit*>* habits, HabitFilter* filter);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\HabitWrapper.h

IMPORT_SOURCE := StringUtils.h