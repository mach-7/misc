#include <iostream>
#include <cassert>
#include <stack>
#include <utility>
using namespace std;



/**
	Class Matcher 
	Author: Maneesh Chauhan
	Version: 0.01
	Notes:

	I have tried to implement an iterative solution to match the test string with 
	a regular expression string containing * and . operators. For this I have used
	a greedy approach in combination with backtracking to stored partial solutions
	using a stack.
*/
class Matcher{

private: 
	// to store ti and ri
	stack<pair<int,int> > myStack;

private:		
	void backTrack(int &a, int &b);
	void pushToStack(int a, int b);
	bool inline areMoreChoices();
public:	
	bool match(const char* testString, const char* regexString);	
	
};


// Method to push the alternative partial solution onto the stack
void Matcher :: pushToStack(int a , int b){
	myStack.push(make_pair(a,b));		
}

// Method to backtrack to a previously stored alternative partial solution
// by returning the indices from where to continue the parsing
void Matcher :: backTrack(int &a, int &b){
	if(!myStack.empty()){
		pair<int,int> mp = myStack.top();
		myStack.pop();
		a = mp.first;
		b = mp.second;		
	}
}

// Private Method to check if there are items stored on myStack
bool Matcher :: areMoreChoices(){
	return !myStack.empty();
}


bool Matcher :: match(const char* testString, const char* regexString){

    // indices to be used to traverse through the testString and regexString respectively	
    int ti = 0 , ri = 0;
		
    // return false if strings are empty
    if((testString == NULL) || (regexString == NULL) ){
	return false;
    }

    // pass or fail shall be returned through this flag
    bool flag = false;

    // push initial indices to the stack
    pushToStack(0,0);	

    // do while stack is not empty
    // loops through all possible parse paths available on the stack
    do		
    { 	
	if(flag){
		return flag;
	} else {
	    // get alternative parse path from the stack and continue	
	    backTrack(ti,ri);
	}

	char prev=0;
	unsigned int consumedReoccuranceCounter=0;
	
        while( (testString[ti] != 0) && (regexString[ri] != 0) ){

	    // compare the characters
	    while(testString[ti] == regexString[ri]){
		
		if(testString[ti] == 0){
	   	    break;
		}	
			
		// Store the character followed by * as prev character
		if(regexString[ri+1] == '*'){
		    prev = regexString[ri];			
	 	}	
		// increment the indices			
	 	++ti;
		++ri;			
	    } // while
		
	    if(regexString[ri] == '*'){
		//cout << "* found prev is: " << prev <<endl;
		// there can be two options
		// either no repeat of previous character
		// or multiple repeats of previous character
		// we have to test for both these conditions	
		// we can go ahead with the first one and put the second on stack
		// and access it if we get a mismatch on the first iteration.
		// once the stack is empty and the outer while gets over, we get a decision
		if(testString[ti] == prev){
		   while(testString[ti] == prev){
 			//cout << testString[ti] << " consumed  " << endl;  
			++ti;
			++consumedReoccuranceCounter;
		   }
		} else {
		   // increment regex index and continue. This means that there is 
		   // 0 reoccurance in test string of prev char
		   ++ri;
		   //cout << " 0 reoccurance of " << prev << endl;
		}
	    } else if(regexString[ri] == '.'){
		// can either ignore the . and increment ri
		// or increment both ri and ti
		// so we have two paths, we will continue with the first and push the second on
		// the stack to be checked later
		// if anyone passes, that means we have successfully matched the strings
		
		pushToStack(ti + 1, ri + 1); // increment both indices and push to stack for later processing
		++ri; // increment ri to next character and continue matching this sequence			
	    } else {
		// this if bloc is for the case like ab*bbc where there are characters 
		// following * operator in regexString that are same as the character preceding it
		// we had maintained a counter for this purpose. We will allow parsing of as many 
		// extra characters in regexString as we had consumed in the testString.
		// This takes care of extra consumptions due to the *.
		if(regexString[ri] == prev ){
		    // check if * consumed any such character
		    while(regexString[ri] == prev){
			if( consumedReoccuranceCounter > 0){
			    ++ri;
			    --consumedReoccuranceCounter;		
			} else {
			  break;
			}
   		    }	
		}
		// checking whether characters at indices ti and ri match in their respective strings
		// if not then we backtrack to the alternative parsing path from the stack
		if(testString[ti] == regexString[ri]){
		    // continue the iteration through the strings as usual	
		    continue;
		} else {
		    // mismatch found, break and test with the alternative path from stack using backtrack()				
		    break;			    	
		}
	    } // else		
	// iterate to test next characters in testString and regexString		
	}

	if( testString[ti] == 0){
            // if the test string is consumed but the regexString is not 
	    // it means mismatch
	    // this is the only place where the base case is satisfied, 
	    // i.e. true value returned if the testString passed the DFA 
	    // represented by the regexString DFA  
	    flag = flag || ( regexString[ri] == 0);
	} 

	if (regexString[ri] == 0){
	    // means the testString has not been consumed fully yet
	    flag = flag || false;
	}

    }while(areMoreChoices());

    // return final value that is essentially a result of ORing the partial results
    // even if one parse path through regexString is able to consume the testString completely	
    // it means the testString matches the regexString
    return flag;
}


int main(){

	Matcher matcher;	

	cout<<"******* Testing Iterative match method  ******\n\n";

	assert(matcher.match("abcd","abcd"));
	cout<<"Test1 Matched \"abcd\" with \"abcd\" \n";

	assert(matcher.match("abbbcd","ab*cd"));
	cout<<"Test2 Matched \"abbbcd\" with \"ab*cd\" \n";

	assert(matcher.match("ABBBBBBBBklmN","ABB*BBkl.N"));
	cout<<"Test3 Matched \"ABBBBBBBBklmN\" with \"ABB*BBkl.N\" \n";

	assert(matcher.match("ABBBDDDkloD","AB*D*kloD"));
	cout<<"Test4 Matched \"ABBBDDDklmN\" with \"AB*D*klmN\" \n";
	

	cout<<"\n*******All tests passed********\n"<<endl;
	return 0;
}

/*
#define MIN(a,b) a<b ? a : b;

    0 1 2 3 4
    A B . K K
    A B K K K

bool match(const char * testString, const char *regexString)
{
    // Check if both the pointers point to valid memory / strings
    if((testString != NULL ) || (regexString != NULL)){
        return False;
    }
    
    // get length of the strings
    int testStringLength = strlen(testString);
    int regexStringLength = strlen(regexString); 
        
    // Now loop through the regexString to match characters with the testString
    
    //int minLen = MIN(testStringLength, regexStringLength);

    bool isMatching = False;
    int i,j = 0 ; // to iterate over regexString
    
    
    for( i = 0 ; (i < testStringLength) && (j < regexStringLength); i++){
        
        char toTest = regexString[j];
        char prev;
        
        if(testString[i] == toTest){
            prev = toTest;
            isMatching = True;
            continue; // continue till end of string for a perfect match or till a special char
        }else{
        
        
   RegexString:     ABB*BBKL
        
      TestString:   ABBBBKL
        
            if( toTest == '*' ){
                // check for 0 case
                // check for reoccurance
                 
                match(testString+ i , RegeXSring+j+1) || prev == testString[i] ? match(testString + i +1, RegesString+j) : false;                 
                               
                if(prev != testString[i]){
                    // 0 occurance case
                    --i; // so that again matching can be done with this character in test String 
                    ++j; // increment to next character in regexString
                    continue;
                } else {
                
                    while((testString[i] == prev) && i < (testStringLength)){
                        ++i; // continue till mismatch
                    }
                    
                    // mismatch found, reset testString pointer, increment regexString pointer
                    --i;
                    ++j;
                    continue;
                }
                              
                
            } else if( toTest == '.'){
               // 0 occurance
               // 1 occurance
               
           bool isSuccess =     match(testString+ i + 1, RegeXSring+j+1) || match(testString + i, RegesString+j+1);
           
           
               
               if(testString[i] == regexString[j+1]){
                   j += 2 ; // move to next to next character in regexstring as next character matches
                   continue;
               } else {
                  // just increment to next character
                  ++j;
                  continue; 
               }             
               
            } else {
                isMatching = False; // mismatch found
                break;    // exit for loop
            }
        }
    }
    
    // what if chars are still left in regexString ??
    return isMatching;
    
}

regexString : * . 

  Example RegexString: ABB*BBkl.D
  testString:          ABBBBBBBkloD
                       ABBBBBBBklD
                       ABBBBklD
                       ABBklD
                       ABBBklD
                       ABBBBklTGPD   

* means 0 or more occurance of previoous character.

. means 0 or 1 occurance of any character.

ABBBBBBklPD

ABBBBklD

ABBPBBlD

ABBBklD

    ABBB*****.DD  * not allowed *
    
    ABB*DFDF*BBB.ddfsfd.GG*fgfg
*/


