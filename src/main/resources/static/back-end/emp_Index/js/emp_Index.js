
function showDivsBasedOnDeptno() {
	    var deptno = document.getElementById('deptNumber').innerText;

	    // 將字串轉換為數字
	    deptno = parseInt(deptno, 10);

	    // 如果 deptno 為 1，顯示所有區塊
	    if (deptno === 1) {
	        document.getElementById('superEmp').style.display = 'block';
	        document.getElementById('act').style.display = 'block';
	        document.getElementById('ven').style.display = 'block';
	        document.getElementById('mem').style.display = 'block';
	        document.getElementById('emp').style.display = 'block';
	    } else {
	        // 否則，根據 deptno 的值顯示相應的區塊
	        if (deptno === 2) {
	            document.getElementById('act').style.display = 'block';
	        }
	        if (deptno === 3) {
	            document.getElementById('ven').style.display = 'block';
	        }
	        if (deptno === 4) {
	            document.getElementById('mem').style.display = 'block';
	        }
	        if (deptno === 5) {
	            document.getElementById('emp').style.display = 'block';
	        }
	    }
	}




var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
        } else {
            dropdownContent.style.display = "block";
        }
    });
}