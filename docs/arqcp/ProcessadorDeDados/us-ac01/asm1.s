.section .data
	
.section .text

.global extract_token # void extract_token(char* input, char* token, int* output);
extract_token:
# input -> rdi
# token -> rsi
# output -> rdx


movq %rsi, %r9	#preserve rsi
movb (%rsi), %cl

loop:
	
	cmpb $0, (%rdi)
	je end
	
	pushq %rdi
	pushq %rsi
	
	cmpb (%rdi), %cl
	je checkToken
	
	increments:
		popq %rsi
		popq %rdi
		addq $1, %rdi
		movb (%rsi), %cl
		jmp loop
	

checkToken:

movq %r9, %rsi

checkInnerLoop:

	cmpb $0, (%rsi)
	je endOfToken
	
	cmpb %cl, (%rdi)
	jne notToken
	
	checkInnerIncrements:
		addq $1, %rdi
		addq $1, %rsi
		movb (%rsi), %cl
		jmp checkInnerLoop
		

endOfToken:
movq %r9, %rsi
addq $1, %rsi
addq $1, %rdi
movq $0, %r8
movq $0, %r9
movl $0, (%rdx)
	
	retrieveValueLoop:
	
		cmpb $0, (%rdi)
		je end
		
		cmpb $'#', (%rdi)
		je end
				
		cmpb $'.', (%rdi)
		je valueIncrements
		
		
		movl (%rdx), %r9d
		
		imull $10, %r9d
		
		movl %r9d, (%rdx)
		
		movb (%rdi), %r8b
		movzbl %r8b, %r8d
		
		cmpb $'y', (%rsi)
		jne seeOtherToken
		movq $-1, (%rdx)
		je end	
		
		retrieveValueIncrements:
		
			addl %r8d, (%rdx)
			
			valueIncrements:

			#addq $4, %rdx
			addq $1, %rdi
			jmp retrieveValueLoop


seeOtherToken:
	cmpb $'n', (%rsi)
	jne isNumberToken
	movq $-1, (%rdx)
	je end
				
isNumberToken:
	subl $'0', %r8d
	jmp retrieveValueIncrements
	
notToken:
	jmp increments

end:
	popq %rsi
	popq %rdi
	ret
