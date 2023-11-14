.section .data

.section .text
	
	.global enqueue_value
	
enqueue_value:
	movl (%rcx), %r9d
	movl %r8d, (%rdi,%r9,4)
	movl (%rdx), %r8d
	
	subl $1, %esi
	
	cmpl %r8d, %r9d 
	je readWriteEqual
	jg writeBigger
	
	addl $1, %r9d
	movl %r9d, (%rcx)
	jmp end

readWriteEqual:
	
	cmpl %esi, %r9d
	jge endOfArrayBoth
	
	addl $1, %r9d
	movl %r9d, (%rcx)
	movl (%rdx), %r9d
	addl $1, %r9d
	movl %r9d, (%rdx)
	jmp end

writeBigger:
	
	cmpl %esi, %r9d
	jle endOfArrayWrite
	
	addl $1, %r9d
	movl %r9d, (%rcx)
	jmp end 
	
endOfArrayWrite:

	movl $0, (%rcx)
	jmp end
	
	
endOfArrayBoth:
	
	movl $0, (%rcx)
	movl $0, (%rdx)
	jmp end

end:

	ret
