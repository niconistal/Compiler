.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
INSIDEFUN1 DB "INSIDE FUN 1",0
fun2_var29 DD ?
SINGLEEXECUTION DB "SINGLE EXECUTION",0
_36 DD 36
fun1_parameter DD ?
fun2_parameter DD ?
INSIDEFUNCTIONCALLBYFOR DB "INSIDE FUNCTION CALL  BY FOR",0
fun2_aux2 DD ?
INSIDEFUN2 DB "INSIDE FUN 2",0
_22 DD 22
fun3_aux2 DD ?
fun3_parameter DD ?
_3 DD 3
_2 DD 2
_1 DD 1
_0 DD 0
_7 DD 7
_6 DD 6
_5 DD 5
_4 DD 4
fun1_var9 DD ?
_9 DD 9
main_count DD ?
fun3_none DD ?
DIO36 DB "DIO 36",0
main_var9 DD ?
_18 DD 18
_16 DD 16
main_var4 DD ?
main_var3 DD ?
main_var2 DD ?
main_var1 DD ?
NODIO22 DB "NO DIO 22",0
DIO22 DB "DIO 22",0
NODIO9 DB "NO DIO 9",0
_20 DD 20
NODIO36 DB "NO DIO 36",0
_10 DD 10
fun1_aux1 DD ?
DIO9 DB "DIO 9",0
 _OFmsg DB "OVERFLOW ",0
rtn DD ?
.code
start:
MOV EBX , _0
MOV main_var1 , EBX
MOV EBX , _2
MOV main_var2 , EBX
MOV EBX , _1
MOV main_var3 , EBX
LEA EBX , [main_var2]
MOV fun2_parameter , EBX
CALL label_fun2
MOV EBX , rtn
MOV main_var1 , EBX
MOV EBX , _9
CMP main_var1 , EBX
JNE MAIN_label_26
invoke MessageBox, NULL, addr DIO9 ,addr DIO9 ,MB_ICONINFORMATION
JMP MAIN_label_28
MAIN_label_26:
invoke MessageBox, NULL, addr NODIO9 ,addr NODIO9 ,MB_ICONINFORMATION
MAIN_label_28:
LEA EBX , [main_var1]
MOV fun1_parameter , EBX
CALL label_fun1
MOV EBX , rtn
MOV main_var1 , EBX
MOV EBX , _9
CMP main_var1 , EBX
JNE MAIN_label_45
invoke MessageBox, NULL, addr DIO9 ,addr DIO9 ,MB_ICONINFORMATION
JMP MAIN_label_47
MAIN_label_45:
invoke MessageBox, NULL, addr NODIO9 ,addr NODIO9 ,MB_ICONINFORMATION
MAIN_label_47:
LEA EBX , [main_var1]
MOV fun2_parameter , EBX
CALL label_fun2
MOV EBX , rtn
MOV main_var2 , EBX
MOV EBX , _36
CMP main_var2 , EBX
JNE MAIN_label_64
invoke MessageBox, NULL, addr DIO36 ,addr DIO36 ,MB_ICONINFORMATION
JMP MAIN_label_66
MAIN_label_64:
invoke MessageBox, NULL, addr NODIO36 ,addr NODIO36 ,MB_ICONINFORMATION
MAIN_label_66:
MOV EBX , main_var3
MOV main_count , EBX
MAIN_label_69:
MOV EBX , _1
ADD EBX , _1
CMP EBX , 65535
JG _overflowed
ADD EBX , _1
CMP EBX , 65535
JG _overflowed
CMP main_count , EBX
JG MAIN_label_94
MOV EBX , _2
MOV main_var4 , EBX
LEA EBX , [main_var4]
MOV fun3_parameter , EBX
CALL label_fun3
MOV EBX , main_count
ADD EBX , _1
CMP EBX , 65535
JG _overflowed
MOV main_count , EBX
JMP MAIN_label_69
MAIN_label_94:
MOV EBX , _3
MOV main_var3 , EBX
MOV EBX , main_var3
MOV main_count , EBX
MAIN_label_100:
MOV EAX , _16
sub edx, edx
idiv _4
imul EAX , _1
CMP EAX ,65535
JG _overflowed
CMP main_count , EAX
JG MAIN_label_130
MOV EBX , _22
MOV main_var9 , EBX
MOV EBX , _22
CMP main_var9 , EBX
JNE MAIN_label_121
invoke MessageBox, NULL, addr DIO22 ,addr DIO22 ,MB_ICONINFORMATION
JMP MAIN_label_123
MAIN_label_121:
invoke MessageBox, NULL, addr NODIO22 ,addr NODIO22 ,MB_ICONINFORMATION
MAIN_label_123:
MOV EBX , main_count
ADD EBX , _1
CMP EBX , 65535
JG _overflowed
MOV main_count , EBX
JMP MAIN_label_100
MAIN_label_130:
MOV EBX , main_var3
MOV main_count , EBX
MAIN_label_133:
MOV EBX , _6
CMP main_count , EBX
JG MAIN_label_147
invoke MessageBox, NULL, addr SINGLEEXECUTION ,addr SINGLEEXECUTION ,MB_ICONINFORMATION
MOV EBX , main_count
ADD EBX , _1
CMP EBX , 65535
JG _overflowed
MOV main_count , EBX
JMP MAIN_label_133
MAIN_label_147:
invoke ExitProcess, 0
_overflowed:
invoke MessageBox, NULL, addr _OFmsg ,addr _OFmsg ,MB_ICONERROR
invoke ExitProcess, 0
label_fun1:
MOV EBX,fun1_parameter
MOV fun1_aux1,EBX
MOV EBX , _0
MOV rtn , EBX
invoke MessageBox, NULL, addr INSIDEFUN1 ,addr INSIDEFUN1 ,MB_ICONINFORMATION
MOV EBX , _0
MOV fun1_var9 , EBX
MOV EBX,fun1_aux1
MOV ECX,[EBX]
ADD ECX , _2
CMP ECX , 65535
JG _overflowed
MOV EAX , _10
sub edx, edx
idiv _2
ADD ECX , EAX
CMP ECX , 65535
JG _overflowed
MOV fun1_var9 , ECX
MOV EBX , fun1_var9
MOV rtn , EBX
ret
label_fun2:
MOV EBX,fun2_parameter
MOV fun2_aux2,EBX
MOV EBX , _0
MOV rtn , EBX
invoke MessageBox, NULL, addr INSIDEFUN2 ,addr INSIDEFUN2 ,MB_ICONINFORMATION
MOV EAX , _2
imul EAX , _3
CMP EAX ,65535
JG _overflowed
ADD EAX , _1
CMP EAX , 65535
JG _overflowed
MOV EBX , EAX
MOV EAX , _20
sub edx, edx
idiv _5
SUB EBX , EAX
MOV EAX , _6
imul EAX , _7
CMP EAX ,65535
JG _overflowed
ADD EBX , EAX
CMP EBX , 65535
JG _overflowed
SUB EBX , _18
ADD EBX , _9
CMP EBX , 65535
JG _overflowed
MOV fun2_var29 , EBX
MOV EBX , fun2_var29
MOV rtn , EBX
ret
label_fun3:
MOV EBX,fun3_parameter
MOV fun3_aux2,EBX
MOV EBX , _0
MOV rtn , EBX
MOV EBX , _0
MOV fun3_none , EBX
invoke MessageBox, NULL, addr INSIDEFUNCTIONCALLBYFOR ,addr INSIDEFUNCTIONCALLBYFOR ,MB_ICONINFORMATION
MOV EBX , fun3_none
MOV rtn , EBX
ret
end start
