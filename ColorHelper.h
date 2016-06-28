
namespace NamespaceName {
	class ClassName {
	public:
	    int intVariable;
	    long longVariable;
		static std::string STATIC_CONST_MEMBER;
		
		std::string getColorStr(int color,long variable2) {
		    variable2 = 2;
			return std::to_string(color);
		}

		int functionIntReturn(){
		    return 0;
		}

		void functionVoidReturn(){
            return;
		}
	};
}
