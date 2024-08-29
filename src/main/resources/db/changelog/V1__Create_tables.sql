-- Create Administrators
CREATE TABLE administrators (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

-- Create Applicants
CREATE TABLE applicants (
      id UUID PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      employment_status VARCHAR(255) NOT NULL,
      marital_status VARCHAR(255) NOT NULL DEFAULT 'unknown',
      gender VARCHAR(255) NOT NULL,
      dob DATE NOT NULL,
      school_level VARCHAR(50),
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      deleted_at TIMESTAMP NULL
  );

-- Create Household Members
  CREATE TABLE household_members (
      id UUID PRIMARY KEY,
      relation VARCHAR(50) NOT NULL, -- example values: 'daughter', 'son'
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      deleted_at TIMESTAMP NULL,
      applicant_id UUID REFERENCES applicants(id), -- parent
      household_member_id UUID REFERENCES applicants(id) -- child
);

-- Create Schemes
CREATE TABLE schemes (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    eligibility_criteria TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

-- Create Benefits
CREATE TABLE benefits (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    amount DECIMAL(10, 2) NULL,
    percentage DECIMAL(5,2) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    scheme_id UUID REFERENCES schemes(id)
);

-- Create Applications
CREATE TABLE applications (
    id UUID PRIMARY KEY,
    applicant_id UUID REFERENCES applicants(id),
    scheme_id UUID REFERENCES schemes(id),
    application_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Pending' -- example values: 'Pending', 'Approved', 'Rejected'
);
