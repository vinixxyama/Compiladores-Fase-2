#include <stdio.h>
#include <string.h>

int main(){
int x;
x = -18;
if(x< 0){
x = 0;
printf(" Negative changed to zero  ");

}else{
if(x== 0){
printf(" Zero found . Changing its value .  ");
x = 2^10;
printf(x);

}else{
printf(" Do nothing  ");

}
}
return 0;
}
