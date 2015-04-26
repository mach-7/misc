#include <iostream>
#include <cassert>
#include <stack>
using namespace std;

struct index{
		int ti;
		int ri;

		index(): ti(0), ri(0){}
		index(int a, int b): ti(a), ri(b) {}
		
		void setValues(int a , int b){
			ti = a, ri = b;
		}

		void operator = (const index & ind ){
			ti = ind.ti;
			ri = ind.ri;
		}
};

bool matchIterative(const char* test, const char* regex){
	assert(test != NULL);
	assert(regex != NULL);
	// first character cannot be a * or .
	assert(*regex != '*');
	assert(*regex != '.');

	// regex index, testString index

	// we just need one valid match amongst all the cases we encounter while regex parsing
	// therefore the final resulting value of the flag will be the logical OR of all the partial results
	bool flag = false;
	// iterate till the end if test string

	index i;
	// using a deque to act as a stack to store intermediate indices for traversing through the strings
	std::stack<index> mystack;
	// seed the stack with initial values
	mystack.push(i);

	while(!mystack.empty()){
		i = mystack.top();
		mystack.pop();	
		

		cout<<(test + i.ti)<<"    "<<(regex + i.ri)<<endl;
	
		while(test[i.ti] != '\0'){
			if(test[i.ti] == regex[i.ri]){
				// increment indices and continue to next characters
				++i.ti , ++i.ri;
				continue;
			} else {
				if(regex[i.ri] == '*'){
					// it can have 0 occurances of previous char or more occurance , we
					// have to consider both cases
				 	 i.setValues(i.ti, i.ri+1);
					mystack.push(i);
					if(regex[i.ri -1] == test[i.ti]){
						i.setValues(i.ti+1,i.ri);
						mystack.push(i);
					}else{
					    flag = flag || false ;	
					    break;			
					}
				}else if(regex[i.ri] == '.'){
					i.setValues(i.ti+1, i.ri+1);
					mystack.push(i);
					i.setValues(i.ti,i.ri+1);
					mystack.push(i);
				}else{
					flag = flag || false;
					break;
				}
			}
		}
		if(test[i.ti] == '\0'){

				flag = flag || regex[i.ri] == '\0';
		}
	}

	// if both, regex string and test string have been completely parsed , this means they match, else mismatch
	return flag;
}


bool match(const char* test, const char* regex){
		// End condition, check if both strings terminate together. If they do, then it is a perfect match
		// else mismatch
		if(*test == '\0'){
			return *regex == '\0';
		}
		// if the characters match, repeat the same test with the next characters
		if(*test == *regex){
			return match(test+1, regex+1);
		} else {
			// if the character do not match, first check if regex character is a * or a .
			// if it is neither, then this signifies a string mismatch, hence return false in else condition
			if(*regex == '*'){
				// * means the previous character can be repeated 0 or more times
				// naturally we test for both the cases
				// first when * may mean 0 repeats, here we just increment regex pointer and proceed with matching
				// second when * means multiple repeats, here we check for multiple occurances in the test string
				// till there is a mismatch
				return match(test,regex+1) || (*test == *(regex-1) ? match(test+1,regex) : false) ;
			} else if(*regex == '.'){
				// . means presence of either 0 or 1 character. The character can be any so we may just increment both
				// pointers to test the next character sequence matching
				// but it may also be the case that . means 0 occurance of a character, so we just increment regex pointer
				// and try to match it with rest of the test string.
				return match(test+1,regex+1) || match(test,regex+1);
			} else{
				// this means a clear mismatch so return false
				return false;
			}		
		}
}

int main(){
	assert(matchIterative("abbbddc","ab*bdd.c"));
	//assert(match("abbbddddddddbdbdbdjhfjf", "ab*d*bdbdbd.hfj."));

	cout<<"Tests Passed";
	return 0;
}

