const API_URL = '/api/students';

document.addEventListener('DOMContentLoaded', () => {
    fetchStudents();
    document.getElementById('student-form').addEventListener('submit', handleFormSubmit);
});

// 1. READ ALL RECORDS (GET)
function fetchStudents() {
    fetch(API_URL)
        .then(res => res.json())
        .then(students => {
            const tbody = document.getElementById('student-table-body');
            tbody.innerHTML = '';

            if (students.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;color:#7f8c8d;">No records found in database.</td></tr>`;
                return;
            }

            students.forEach(student => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td><strong>${student.studentId}</strong></td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td>${student.email}</td>
                    <td>${student.enrollmentDate}</td>
                    <td class="actions">
                        <button class="action-btn edit-btn" onclick='prepareUpdate(${JSON.stringify(student)})'>Edit</button>
                        <button class="action-btn delete-btn" onclick="deleteStudent(${student.studentId})">Delete</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(() => showToast("Error connecting to server pipeline.", true));
}

// 2. CREATE & UPDATE ROUTER (POST / PUT)
function handleFormSubmit(e) {
    e.preventDefault();

    const id = document.getElementById('student-id').value;
    const payload = {
        firstName: document.getElementById('first-name').value,
        lastName: document.getElementById('last-name').value,
        email: document.getElementById('email').value
    };

    let method = 'POST';
    if (id) {
        method = 'PUT';
        payload.studentId = parseInt(id);
    }

    fetch(API_URL, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
        .then(async response => {
            if (!response.ok) {
                // Automatically grabs the secure exception text from your GlobalExceptionHandler!
                const errMsg = await response.text();
                throw new Error(errMsg);
            }
            return response.json();
        })
        .then(() => {
            showToast(id ? "Record modified cleanly!" : "Student profile added successfully!");
            resetForm();
            fetchStudents();
        })
        .catch(err => showToast(err.message, true));
}

// 3. DELETE OPERATION (DELETE)
function deleteStudent(id) {
    if (confirm(`Are you sure you want to completely remove student ID ${id}?`)) {
        fetch(`${API_URL}/${id}`, { method: 'DELETE' })
            .then(async response => {
                if (!response.ok) throw new Error("Could not drop row.");
                showToast("Record cleanly dropped from MySQL.");
                fetchStudents();
            })
            .catch(err => showToast(err.message, true));
    }
}

// Populate interface values for editing
function prepareUpdate(student) {
    document.getElementById('form-title').innerText = "Modify Student Profile";
    document.getElementById('student-id').value = student.studentId;
    document.getElementById('first-name').value = student.firstName;
    document.getElementById('last-name').value = student.lastName;
    document.getElementById('email').value = student.email;

    document.getElementById('submit-btn').innerText = "Apply Changes";
    document.getElementById('cancel-btn').style.display = 'block';
}

function resetForm() {
    document.getElementById('form-title').innerText = "Register New Student";
    document.getElementById('student-id').value = '';
    document.getElementById('student-form').reset();
    document.getElementById('submit-btn').innerText = "Save Record";
    document.getElementById('cancel-btn').style.display = 'none';
}

// Banner alert logic
function showToast(msg, isError = false) {
    const banner = document.getElementById('notification');
    banner.innerText = msg;
    banner.className = isError ? 'error' : 'success';
    banner.style.display = 'block';
    setTimeout(() => { banner.style.display = 'none'; }, 4000);
}