UPDATE Ward SET availability = 1
  WHERE ward_number = 4;
        
INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  5,
  4,
  4,
  3
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  6,
  4,
  4,
  3
);



INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  102,
  5
);

INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  106,
  6
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
  status
) VALUES (
  1005,
  'Sam',
  'Jones',
  '111-22-3333',
  'M',
  '1990-05-20',
  '919-222-3333',
  '11 XYZ St',
  'Raleigh',
  'NC',
  '27753',
  'processing treatment plan 20'
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
  status
) VALUES (
  1006,
  'Jim',
  'West',
  '222-33-4444',
  'M',
  '1995-01-02',
  '919-111-2222',
  '33 RS St ',
  'Raleigh',
  'NC',
  '27754',
  'processing treatment plan 10'
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
  status
) VALUES (
  1007,
  'Laura',
  'East',
  '333-44-5555',
  'F',
  '1985-02-12',
  '919-333-4444',
  '21 LM St',
  'Raleigh',
  'NC',
  '27765',
  'processing treatment plan 05'
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
  status
) VALUES (
  1008,
  'Lori',
  'South',
  '444-55-6666',
  'F',
  '1998-02-05',
  '919-444-5555',
  '45 GH St',
  'Raleigh',
  'NC',
  '27766',
  'processing treatment plan 09'
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
  status
) VALUES (
  1009,
  'Oliver',
  'North',
  '123-45-6789',
  'M',
  '1990-05-27',
  '919-555-6666',
  '67 AM St',
  'Raleigh',
  'NC',
  '27788',
  'processing treatment plan 10'
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
  status
) VALUES (
  1010,
  'Always',
  'Right',
  '234-56-7890',
  'F',
  '1987-02-25',
  '919-666-7777',
  '54 E St',
  'Raleigh',
  'NC',
  '27766',
  'processing treatment plan 10'
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
  status
) VALUES (
  1011,
  'Far',
  'Left',
  '345-67-0987',
  'M',
  '1991-02-01',
  '919-777-8888',
  '61 F St',
  'Raleigh',
  'NC',
  '27766',
  'processing treatment plan 15'
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
  status
) VALUES (
  1012,
  'Donald',
  'Trump',
  '543-45-1234',
  'M',
  '1945-01-25',
  '919-911-9111',
  '1 Penn Ave',
  'Washington',
  'DC',
  '12345',
  'processing treatment plan 20'
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
  status
) VALUES (
  1013,
  'Al',
  'Gore',
  '321-65-0987',
  'M',
  '1948-05-28',
  '345-111-1234',
  '1 Loser Ln',
  'Nowheresville',
  'AK',
  '33300',
  'processing treatment plan 15'
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
  status
) VALUES (
  1014,
  'Roy',
  'Rogers',
  '221-22-2211',
  'M',
  '1940-04-05',
  '212-212-2122',
  '2 Lasso Lane',
  'Rodeo',
  'TX',
  '43421',
  'processing treatment plan 18'
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
  status
) VALUES (
  1015,
  'John',
  'Rambo',
  '444-55-5454',
  'M',
  '1960-05-30',
  '112-112-1112',
  '3 Combat Road',
  'Timbuktoo',
  'FL',
  '32345',
  'processing treatment plan 20'
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
  status
) VALUES (
  1016,
  'Jason',
  'Bourne',
  '989-98-9898',
  'M',
  '1992-04-05',
  '999-888-7777',
  '4 Spy Lane',
  'Paris',
  'TN',
  '34543',
  'processing treatment plan 20'
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
  status
) VALUES (
  1017,
  'Jack',
  'Bauer',
  '111-22-3334',
  'M',
  '1991-05-20',
  '121-321-4343',
  '5 Undercover St',
  'Washington',
  'DC',
  '32345',
  'processing treatment plan 20'
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
  check_in_date
) VALUES (
  5,
  1005,
  '111-22-3333',
  'Credit card',
  4044-9876-1234-9123,
  NULL,
  '11 XYZ St',
  'Raleigh',
  'NC',
  '27753',
  '2019-01-04'
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
  check_in_date
) VALUES (
  6,
  1006,
  '222-33-4444',
  'Credit card',
  4044-8754-0961-3234,
  NULL,
  '33 RS St',
  'Raleigh',
  'NC',
  '27754',
  '2018-12-25'
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
  check_in_date
) VALUES (
  7,
  1007,
  '333-44-5555',
  'Credit card',
  4401-9823-9854-1143,
  NULL,
  '21 LM St',
  'Raleigh',
  'NC',
  '27765',
  '2019-01-23'
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
  check_in_date
) VALUES (
  8,
  1008,
  '444-55-6666',
  'Check',
  NULL,
  NULL,
  '45 GH St',
  'Raleigh',
  'NC',
  '27766',
  '2019-02-05'
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
  check_in_date
) VALUES (
  9,
  1009,
  '123-45-6789',
  'Check',
  NULL,
  NULL,
  '67 AM St',
  'Raleigh',
  'NC',
  '27788',
  '2019-01-28'
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
  check_in_date
) VALUES (
  10,
  1010,
  '234-56-7890',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '54 E St',
  'Raleigh',
  'NC',
  '27766',
  '2019-02-27'
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
  check_in_date
) VALUES (
  11,
  1011,
  '345-67-0987',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '61 F St',
  'Raleigh',
  'NC',
  '27766',
  '2019-01-28'
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
  check_in_date
) VALUES (
  12,
  1012,
  '543-45-1234',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '1 Penn Ave',
  'Washington',
  'DC',
  '12345',
  '2018-12-25'
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
  check_in_date
) VALUES (
  13,
  1013,
  '321-65-0987',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '1 Loser Lane',
  'Nowheresville',
  'AK',
  '33300',
  '2019-02-25'
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
  check_in_date
) VALUES (
  14,
  1014,
  '221-22-2211',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '2 Lasso Lane',
  'Rodeo',
  'TX',
  '43421',
  '2019-03-23'
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
  check_in_date
) VALUES (
  15,
  1015,
  '444-55-5454',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '3 Combat Road',
  'Timbuktoo',
  'FL',
  '32345',
  '2019-03-25'
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
  check_in_date
) VALUES (
  16,
  1016,
  '989-98-9898',
  'Credit Card',
  4343-4545-5555-3333,
  NULL,
  '4 Spy Lane',
  'Paris',
  'TN',
  '34543',
  '2019-04-09'
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
  check_in_date
) VALUES (
  17,
  1017,
  '111-22-3334',
  'Credit Card',
  '4343-4545-5555-3333',
  NULL,
  '5 Undercover St',
  'Washington',
  'DC',
  '32345',
  '2019-04-04'
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
  check_in_date
) VALUES (
  18,
  1001,
  '000-01-1234',
  'Credit Card',
  '4044-8754-0961-3234',
  NULL,
  '69 ABC St',
  'Raleigh',
  'NC',
  '27730',
  '2018-11-05'
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
  check_in_date
) VALUES (
  19,
  1002,
  '000-02-1234',
  'Credit Card',
  '4401-9823-9854-1143',
  NULL,
  '81 DEF St',
  'Cary',
  'NC',
  '27519',
  '2018-10-15'
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
  check_in_date
) VALUES (
  20,
  1003,
  '000-02-1234',
  'Check',
  NULL,
  NULL,
  '31 OPG St',
  'Cary',
  'NC',
  '27519',
  '2018-09-10'
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
  check_in_date
) VALUES (
  21,
  1002,
  '000-02-1234',
  'Check',
  NULL,
  NULL,
  '81 DEF St',
  'Cary',
  'NC',
  '27519',
  '2018-08-10'
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
  check_in_date
) VALUES (
  22,
  1016,
  '989-98-9898',
  'Credit Card',
  '4343-4545-5555-3333',
  NULL,
  '4 Spy Lane',
  'Paris',
  'TN',
  '34543',
  '2019-01-02'
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
  check_in_date
) VALUES (
  23,
  1017,
  '111-22-3334',
  'Credit Card',
  '4343-4545-5555-3333',
  NULL,
  '5 Undercover St',
  'Washington',
  'DC',
  '32345',
  '2019-02-12'
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
  check_in_date
) VALUES (
  24,
  1004,
  '000-04-1234',
  'Credit Card',
  '4044-9876-1234-9123',
  NULL,
  '10 TBC St',
  'Raleigh',
  'NC',
  '27730',
  '2019-04-05'
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  5,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
    account_number,
    billing_record_number,
    fee_description,
    fee
  ) VALUES (
    5,
    2,
    'accommodation fee',
    200
  );

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  6,
  1,
  'registration fee',
  100
);


INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  6,
  2,
  'accommodation fee',
  800
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  7,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  7,
  2,
  'accommodation fee',
  650
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  8,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  8,
  2,
  'accommodation fee',
  400
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  9,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  9,
  2,
  'accommodation fee',
  800
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  10,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  10,
  2,
  'accommodation fee',
  600
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  11,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  11,
  2,
  'accommodation fee',
  2250
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  12,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  12,
  2,
  'accommodation fee',
  4900
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  13,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  13,
  2,
  'accommodation fee',
  1850
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  14,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  14,
  2,
  'accommodation fee',
  150
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  15,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  15,
  2,
  'accommodation fee',
  500
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  16,
  1,
  'registration fee',
  100
);


INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  17,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  18,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  18,
  2,
  'accommodation fee',
  450
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  19,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  19,
  2,
  'accommodation fee',
  500
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  20,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  20,
  2,
  'accommodation fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  21,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  21,
  2,
  'accommodation fee',
  250
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  22,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  22,
  2,
  'accommodation fee',
  400
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  23,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  23,
  2,
  'accommodation fee',
  150
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  24,
  1,
  'registration fee',
  100
);


INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1005,
  '2019-01-04',
  '2019-01-08',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1006,
  '2018-12-25',
  '2019-01-10',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL
);



INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1007,
  '2019-01-23',
  '2019-02-05',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  100
);



INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1008,
  '2019-02-05',
  '2019-02-09',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  100
);


INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1009,
  '2019-01-28',
  '2019-02-05',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1010,
  '2019-02-27',
  '2019-03-05',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1011,
  '2019-01-28',
  '2019-03-14',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1012,
  '2018-12-25',
  '2019-04-02',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1013,
  '2019-02-25',
  '2019-04-03',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1014,
  '2019-03-23',
  '2019-03-26',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1015,
  '2019-03-25',
  '2019-04-04',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1016,
  '2019-04-09',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1017,
  '2019-04-04',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1001,
  '2018-11-05',
  '2018-11-10',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1002,
  '2018-10-15',
  '2018-10-25',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1003,
  '2018-09-10',
  '2018-09-12',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  4,
  'checkin',
  1002,
  '2018-08-10',
  '2018-08-15',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1016,
  '2019-01-02',
  '2019-01-10',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1017,
  '2019-02-12',
  '2019-02-15',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
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
  'checkin',
  1004,
  '2019-04-05',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

UPDATE Bed SET availability = 0 
   WHERE bed_number = 1 AND
         ward_number = 4;
         

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  5,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  5,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  3,
  5,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  4,
  5,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  6,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  6,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  3,
  6,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  4,
  6,
  1
);


INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1005,
  2,
  2,
  '2019-01-04',
  '2019-01-08',
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
  1006,
  3,
  2,
  '2018-12-25',
  '2019-01-10',
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
  1007,
  4,
  2,
  '2019-01-23',
  '2019-02-05',
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
  1008,
  2,
  3,
  '2019-02-05',
  '2019-02-09',
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
  1009,
  1,
  4,
  '2019-01-28',
  '2019-02-05',
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
  1010,
  2,
  4,
  '2019-02-27',
  '2019-03-05',
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
  1011,
  1,
  5,
  '2019-01-28',
  '2019-03-14',
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
  1012,
  2,
  5,
  '2018-12-25',
  '2019-04-02',
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
  1013,
  3,
  5,
  '2019-02-25',
  '2019-04-03',
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
  1014,
  4,
  5,
  '2019-03-23',
  '2019-03-26',
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
  1015,
  1,
  6,
  '2019-03-25',
  '2019-04-04',
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
  1016,
  1,
  4,
  '2019-04-09',
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
  1017,
  2,
  5,
  '2019-04-04',
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
  1001,
  3,
  1,
  '2018-11-05',
  '2018-11-10',
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
  1002,
  4,
  1,
  '2018-10-15',
  '2018-10-25',
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
  1003,
  1,
  1,
  '2018-09-10',
  '2018-09-12',
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
  1002,
  4,
  1,
  '2018-08-10',
  '2018-08-15',
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
  1016,
  3,
  6,
  '2019-01-12',
  '2019-01-10',
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
  1017,
  4,
  6,
  '2019-02-12',
  '2019-02-15',
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
  1004,
  2,
  6,
  '2019-04-05',
  NULL,
  'Reserved'
);
