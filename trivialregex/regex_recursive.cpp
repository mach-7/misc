#include <iostream>
#include <cassert>
using namespace std;



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
	assert(match("abbbddddddddbdbdbdjhfjf", "ab*d*bdbdbd.hfj."));
	cout<<"Tests Passed";

	return 0;
}

