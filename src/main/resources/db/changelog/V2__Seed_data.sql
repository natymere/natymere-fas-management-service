-- Insert Applicants
INSERT INTO applicants (id, name, employment_status, marital_status, gender, dob, school_level) VALUES
('01913b7a-4493-74b2-93f8-e684c4ca935c', 'James', 'UNEMPLOYED', 'SINGLE', 'MALE', '1990-07-01', 'UNIVERSITY'),
('01913b80-2c04-7f9d-86a4-497ef68cb3a0', 'Mary', 'UNEMPLOYED', 'MARRIED', 'FEMALE', '1984-10-06', 'SECONDARY'),
('01913b88-1d4d-7152-a7ce-75796a2e8ecf', 'Gwen', 'UNEMPLOYED', 'SINGLE', 'FEMALE', '2016-02-01', 'PRIMARY'),
('01913b88-65c6-7255-820f-9c4dd1e5ce79', 'Jayden', 'UNEMPLOYED', 'SINGLE', 'MALE', '2018-03-15', 'PRIMARY');

-- Insert Household Members
INSERT INTO household_members (id, applicant_id, household_member_id, relation) VALUES
('123e4567-e89b-12d3-a456-426614174000', '01913b80-2c04-7f9d-86a4-497ef68cb3a0', '01913b88-1d4d-7152-a7ce-75796a2e8ecf', 'DAUGHTER'),
('123e4567-e89b-12d3-a456-426614174001', '01913b80-2c04-7f9d-86a4-497ef68cb3a0', '01913b88-65c6-7255-820f-9c4dd1e5ce79', 'SON');

-- Insert Schemes
INSERT INTO schemes (id, name, description, eligibility_criteria) VALUES
('01913b89-9a43-7163-8757-01cc254783f3', 'Retrenchment Assistance Scheme', 'Assistance for unemployed individuals', '{"employment_status": "UNEMPLOYED", "marital_status": "SINGLE"}'),
('01913b89-befc-7ae3-bb37-3079aa7f1be0', 'Retrenchment Assistance Scheme (families)', 'Assistance for unemployed families with school-going children', '{"employment_status": "UNEMPLOYED", "has_children": {"school_level": "PRIMARY"}}');

-- Insert Benefits
INSERT INTO benefits (id, scheme_id, name, description, amount, percentage) VALUES
('01913b8b-9b12-7d2c-a1fa-ea613b802ebc', '01913b89-9a43-7163-8757-01cc254783f3', 'SkillsFuture Credits', 'Additional SkillsFuture credits', 500.00, NULL),
('d420da4c-158f-48f4-aa03-517b4923b64e', '01913b89-9a43-7163-8757-01cc254783f3', 'CDC Vouchers', 'Additional CDC vouchers', 300.00, NULL),
('3fac749e-031e-4641-aeb8-8e80cf5aa8ec', '01913b89-befc-7ae3-bb37-3079aa7f1be0', 'Daily School Meal Vouchers', 'Daily school meal vouchers for applicants (for applicants with children attending primary school only)', 100.00, NULL),
('4dcac6ba-1374-43d5-be57-0157a0303e74', '01913b89-befc-7ae3-bb37-3079aa7f1be0', 'CDC Vouchers', 'Additional CDC vouchers', 500.00, NULL);

INSERT INTO administrators (id, username, password, full_name, email, role, created_at, updated_at)
VALUES ('a56b15fc-b2c6-4b83-b615-3e3c77dd4f73', 'admin', '{noop}coolbeans', 'Administrator', 'admin@example.com', 'ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
