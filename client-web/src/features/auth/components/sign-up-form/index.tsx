import React, {
    type FC, type ChangeEventHandler, useState, type FormEventHandler,
} from "react";
import {
    Box, MenuItem, TextField, Typography, Button,
} from "@mui/material";
import { IMaskInput } from "react-imask";

import { type CreateAddressDto, type SignInDto, type SignUpDto } from "@/features/auth/models";
import { useAuthContext } from "@/features/auth/contexts/auth";

const TextMaskCustom = React.forwardRef<HTMLElement, any>(
    (props, ref) => {
        const { onChange, ...other } = props;
        return (
            <IMaskInput
                {...other}
                mask="+7 (###) ###-##-##"
                definitions={{
                    "#": /[0-9]/,
                }}
                inputRef={ref}
                onAccept={(value: any) => onChange({ target: { name: props.name, value } })}
                overwrite
            />
        );
    },
);

const PASSWORD_MAX_LENGTH = 50;
const USERNAME_MAX_LENGTH = 200;
const ADDRESS_LINE_MAX_LENGTH = 500;

type Data = Pick<SignInDto, "password"> & Pick<SignUpDto, "username"> & CreateAddressDto & {
    localityId: number;
    phone: string;
};

const DEFAULT_DATA: Data = {
    addressLine: "",
    localityId: 1,
    password: "",
    phone: "7",
    username: "",
};

export const SignUpForm: FC = () => {
    const { cities, signUp } = useAuthContext();

    const [data, setData] = useState<Data>(DEFAULT_DATA);

    const handlePhoneChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, phone: target.value }));
    };

    const handlePasswordChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, password: target.value }));
    };

    const handleUsernameChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, username: target.value }));
    };

    const handleLocalityChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, localityId: +target.value }));
    };

    const handleAddressLineChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, addressLine: target.value }));
    };

    const handleSubmit: FormEventHandler = (event) => {
        event.preventDefault();

        const dto: SignUpDto = {
            address: {
                addressLine: data.addressLine,
            },
            localityId: data.localityId,
            password: data.password,
            phone: data.phone.replaceAll(/\+|\ |\(|\)|\-/gi, ""),
            username: data.username,
        };

        signUp(dto);
    };

    return (
        <form onSubmit={handleSubmit}>
            <Box minWidth={400} sx={{ display: "flex", flexDirection: "column", rowGap: 2 }}>
                <Typography variant="h5">Sign Up</Typography>

                <TextField
                    value={data.phone}
                    onChange={handlePhoneChange}
                    InputProps={{
                        inputComponent: TextMaskCustom,
                    }}
                />

                <TextField
                    value={data.password}
                    label="Password"
                    variant="outlined"
                    onChange={handlePasswordChange}
                    type="password"
                    inputProps={{ maxLength: PASSWORD_MAX_LENGTH }}
                />

                <TextField
                    value={data.username}
                    label="Your name"
                    variant="outlined"
                    onChange={handleUsernameChange}
                    inputProps={{ maxLength: USERNAME_MAX_LENGTH }}
                />

                <TextField
                    value={data.localityId}
                    onChange={handleLocalityChange}
                    select
                    label="The locality you live in"
                >
                    {cities.map(({ id, name }) => (
                        <MenuItem key={id} value={id}>{name}</MenuItem>
                    ))}
                </TextField>

                <TextField
                    value={data.addressLine}
                    label="Address line"
                    variant="outlined"
                    onChange={handleAddressLineChange}
                    inputProps={{ maxLength: ADDRESS_LINE_MAX_LENGTH }}
                />

                <Button type="submit" variant="contained">
                    Submit
                </Button>
            </Box>
        </form>
    );
};
