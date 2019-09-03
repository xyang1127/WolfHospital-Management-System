INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  1,
  100
);

INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  2,
  50
);

INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  4,
  20
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  1,
  1,
  10,
  5
);

INSERT INTO Ward (
  ward_number,  
  ward_type,
  capacity,
  availability
) VALUES (
  2,
  2,
  18,
  3
);

INSERT INTO Ward (
  ward_number,  
  ward_type,
  capacity,
  availability
) VALUES (
  3,  
  4,
  64,
  23
);

INSERT INTO Ward (
  ward_number,  
  ward_type,
  capacity,
  availability
) VALUES (
  4,  
  2,
  24,
  6
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  1,
  'Smith',
  'John',
  '1995-01-01',
  'M',
  'Doctor',
  '919-222-2345',
  'Senior surgeon',
  'Oncological surgery',
  '222 South Street',
  'Raleigh',
  'NC',
  '27615'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  2,
  'Doe',
  'Jane',
  '1998-02-05',
  'F',
  'Nurse',
  '678-233-8787',
  'Nurse supervisor',
  'Orthppedics',
  '4901 Harbor Street',
  'Atlanta',
  'GA',
  '37760'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  3,
  'Smith',
  'Sally',
  '1997-01-05',
  'F',
  'Nurse',
  '919-222-2346',
  'OR nurse',
  'General surgery',
  '222 South Street',
  'Raleigh',
  'NC',
  '27615'
);

INSERT INTO Staff ( 
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  4,
  'Jones',
  'Sam',
  '1960-05-25',
  'M',
  'Doctor',
  '919-234-5645',
  'Orthopedic surgeon',
  'Orthopedics',
  '333 Falls of the Neuse',
  'Raleigh',
  'NC',
  '27614'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  5,
  'Hale',
  'Lynette',
  '1995-11-12',
  'F',
  'Receptionist',
  '919-455-0987',
  'Receptionist',
  'Administration',
  '15690 North Avanue',
  'Raleigh',
  'NC',
  '29609'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  6,
  'Brown',
  'Jenny',
  '1997-10-05',
  'F',
  'Receptionist',
  '919-677-8765',
  'Receptionist',
  'Administration',
  '300 Six Forks Road',
  'Raleigh',
  'NC',
  '27611'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  7,
  'Strange',
  'Ben',
  '1985-07-18',
  'M',
  'Doctor',
  '919-876-0987',
  'Family doctor',
  'General medicine',
  '2567 Falls River Avenue',
  'Raleigh',
  'NC',
  '27613'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  8,
  'Williams',
  'Jerry',
  '1975-08-15',
  'M',
  'Doctor',
  '919-666-9876',
  'Oncologist',
  'Oncology',
  '1536 State Street',
  'Raleigh',
  'NC',
  '27612'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  9,
  'Johnson',
  'Amy',
  '1998-09-15',
  'F',
  'Nurse',
  '919-212-9090',
  'Nurse',
  'General medicine',
  '5454 Rogers Road',
  'Wake Forest',
  'NC',
  '27614'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  10,
  'McNamara',
  'Carol',
  '1991-04-15',
  'F',
  'Nurse',
  '919-434-9890',
  'Nurse',
  'General medicine',
  '4321 Oak Street',
  'Wake Forest',
  'NC',
  '27614'
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  1,
  'general surgery'
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  4,
  'orthopedic surgery'
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  8,
  'oncology'
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  7,
  'general medicine'
);

INSERT INTO Nurse (
  staff_id,
  ward_number
) VALUES (
  2,
  1
);

INSERT INTO Nurse (
  staff_id,
  ward_number
) VALUES (
  3,
  2
);

INSERT INTO Nurse (
  staff_id,
  ward_number
) VALUES (
  9,
  4
);

INSERT INTO Nurse (
  staff_id,
  ward_number
) VALUES (
  10,
  NULL
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  1,
  'Andrew',
  'Aldridge',
  '111-11-1111',
  'M',
  '1982-05-22',
  '142-193-1949',
  '123 Alden Street',
  'Auburn',
  'AL',
  '36830',
  'in treatment',
  1
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  2,
  'Betty',
  'Belford',
  '222-22-2222',
  'F',
  '1972-12-01',
  '942-412-7224',
  '456 Baldwin Street',
  'Brooklyn',
  'NY',
  '11213',
  'in treatment',
  4
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  3,
  'Carl',
  'Crawford',
  '333-33-3333',
  'M',
  '1969-10-13',
  '148-142-6993',
  '789 Canal Street',
  'Calabasas',
  'CA',
  '91301',
  'awaiting treatment',
  7
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  4,
  'Debbie',
  'Dun',
  '444-44-4444',
  'F',
  '2001-02-22',
  '581-402-5921',
  '1842 David Road',
  'Dover',
  'DE',
  '19901',
  'awaiting room assignment',
  NULL
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  5,
  'Edward',
  'Elkin',
  '555-55-5555',
  'M',
  '1988-02-28',
  '+44 1632 960146',
  '1 Eagle Court',
  'Eagleton',
  'IN',
  '49212',
  'in treatment',
  7
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  6,
  'Frances',
  'Freedman',
  '666-66-6666',
  'F',
  '1973-07-31',
  '132-516-1621',
  '019 Frederick Circle',
  'Fort Myers',
  'FL',
  '33900',
  'awaiting treatment',
  8
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status,
  assigned_doctor
) VALUES (
  7,
  'Greg',
  'Goldenhower',
  '777-77-7777',
  'M',
  '1932-01-31',
  '039-130-1030',
  '29 Golden Drive',
  'Griffin',
  'GA',
  '30223',
  'awaiting treatment',
  4
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date,
  check_out_date
) VALUES (
  1,
  1,
  '111-22-3333',
  'cash',
  NULL,
  NULL,
  '221B Baker Street',
  'Raleigh',
  'North Carolina',
  '12345',
  '2015-03-14',
  '2015-04-20'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date,
  check_out_date
) VALUES (
  2,
  2,
  '222-33-4444',
  'credit card',
  '1111-2222-3333-4444',
  'Unitedhealth Group',
  '40th Ave',
  'New York City',
  'New York',
  '54321',
  '2017-02-23',
  '2017-03-16'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date,
  check_out_date
) VALUES (
  3,
  3,
  '333-44-5555',
  'debt card',
  '2222-3333-4444-5555',
  'Aetna Group',
  '123 Western Street',
  'Los Angeles',
  'California',
  '23456',
  '2018-11-27',
  '2019-01-01'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date,
  check_out_date
) VALUES (
  4,
  4,
  '555-66-7777',
  'credit card',
  '9999-8888-7777-6666',
  NULL,
  '111 Maple Street',
  'Raleigh',
  'North Carolina',
  '00001',
  '2019-01-09',
  '2019-01-25'
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee 
) VALUES (
  1,
  1,
  'Advil',
  10.99
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  2,
  2,
  'Bandaid',
  10.99
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  2,
  1,
  'Bengay',
  19.99
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  4,
  2,
  'Aspirin',
  10.99
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  1,
  3,
  'Crutches',
  250.00
);


INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  1,
  '2005-12-05',
  '2006-01-23',
  'Patient recieved treatment for the influenza virus',
  'Tamiflu 75mg',
  NULL,
  NULL,
  NULL,
  7
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  1,
  '2007-03-27',
  '2007-05-23',
  'Patient recieved treatment for a wrist fracture',
  NULL,
  NULL,
  NULL,
  NULL,
  4
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  2,
  '2012-12-01',
  '2012-12-01',
  'Patient was diagnosed with mild conjunctivitous',
  'moxifloxin',
  NULL,
  NULL,
  NULL,
  7
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  2,
  '2005-12-05',
  '2005-12-05',
  'Patient has a severe case of hypochondria',
  NULL,
  NULL,
  NULL,
  NULL,
  7
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  3,
  1,
  '2005-12-08',
  '2005-12-10',
  NULL,
  NULL,
  8,
  'Scheduled',
  NULL,
  7
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  3,
  2,
  '2005-12-08',
  '2005-12-10',
  NULL,
  NULL,
  8,
  'Completed',
  'Benign',
  1
);

INSERT INTO MedicalRecord (
  record_id,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  3,
  '2005-11-25',
  '2005-11-30',
  NULL,
  NULL,
  8,
  'Completed',
  'Malignant',
  1
);
INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  1,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  1,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  3,
  1,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  2,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  2,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  10,
  3,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  11,
  3,
  0
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1,
  1,
  1,
  '2015-03-13',
  '2015-04-20',
  'Reserved'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  2,
  2,
  1,
  '2017-02-23',
  '2017-02-24',
  'Reserved'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  2,
  2,
  1,
  '2017-02-24',
  '2017-02-28',
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  3,
  1,
  2,
  '2018-11-27',
  '2018-11-28',
  'Reserved'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  3,
  1,
  2,
  '2018-11-28',
  '2018-12-01',
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  4,
  2,
  2,
  '2019-01-09',
  '2019-01-10',
  'Reserved'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  4,
  2,
  2,
  '2019-01-10',
  NULL,
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  5,
  1,
  1,
  '2019-02-10',
  NULL,
  'Reserved'
);