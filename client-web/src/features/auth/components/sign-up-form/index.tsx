import React, {
    memo, type FC, type ChangeEventHandler, useState, FormEventHandler,
} from "react";
import {
    Box, MenuItem, Select, type SelectChangeEvent, TextField, Typography, Button,
} from "@mui/material";
import type { CreateAddressDto, SignInDto, SignUpDto } from "features/auth/models";
import { useAuthContext } from "features/auth/contexts/auth";
import { IMaskInput } from "react-imask";

// ... copypasted
const TextMaskCustom = React.forwardRef<HTMLElement, any>( // TODO any -> CustomProps
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

const PASSWORD_MAX_LENGTH = 50; // TODO unify with sign up
// ...
const USERNAME_MAX_LENGTH = 200;
const ADDRESS_LINE_MAX_LENGTH = 500;

// TODO rename
type Data = Pick<SignInDto, "password"> & Pick<SignUpDto, "username"> & CreateAddressDto & {
    phone: string;
};

const DEFAULT_DATA: Data = {
    addressLine: "",
    cityId: 1, // TODO 1?
    password: "",
    phone: "7",
    username: "",
};

// TODO minWidth duplicated
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

    const handleCityChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, cityId: +target.value }));
    };

    const handleAddressLineChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, addressLine: target.value }));
    };

    const handleSubmit: FormEventHandler = (event) => {
        event.preventDefault();

        const dto: SignUpDto = {
            // ...data,
            address: {
                addressLine: data.addressLine,
                cityId: data.cityId,
            },
            password: data.password,
            phone: data.phone.replaceAll(/\+|\ |\(|\)|\-/gi, ""), // TODO copypasted
            username: data.username
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
                    value={data.cityId}
                    onChange={handleCityChange}
                    select // tell TextField to render select
                    label="Your city"
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
