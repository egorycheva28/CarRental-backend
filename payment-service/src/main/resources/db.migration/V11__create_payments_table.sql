CREATE TABLE payments(
       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
       create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       edit_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       status_payment VARCHAR NOT NULL,
       user_id UUID NOT NULL,
       booking_id UUID NOT NULL
);