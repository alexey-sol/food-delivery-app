import {
    Box, Button, FormControl, Input, Link, TextField, Typography,
} from "@mui/material";
import { useAuthContext } from "@/features/auth/contexts/auth";
import { SignInDto } from "@/features/auth/models";
import React, {
    memo, type FC, useState, ChangeEventHandler, FormEventHandler,
} from "react";
import { IMaskInput } from "react-imask";
import { AppLink } from "@/shared/components/app-link";
import { url } from "@/shared/const";

interface CustomProps {
    onChange: (event: { target: { name: string; value: string } }) => void;
    name: string;
}

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

type Data = Pick<SignInDto, "password"> & {
    phone: string;
};

const DEFAULT_DATA: Data = {
    password: "",
    phone: "7",
};

export const SignInForm: FC = () => {
    const { signIn } = useAuthContext();

    const [data, setData] = useState<Data>(DEFAULT_DATA);

    const handlePasswordChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, password: target.value }));
    };

    const handlePhoneChange: ChangeEventHandler<HTMLInputElement> = ({ target }) => {
        setData((oldData) => ({ ...oldData, phone: target.value }));
    };

    const handleSubmit: FormEventHandler = (event) => {
        event.preventDefault();

        const dto: SignInDto = {
            ...data,
            phone: data.phone.replaceAll(/\+|\ |\(|\)|\-/gi, ""),
        };

        signIn(dto);
    };

    return (
        <form onSubmit={handleSubmit}>
            <Box minWidth={400} sx={{ display: "flex", flexDirection: "column", rowGap: 2 }}>
                <Typography variant="h5">Sign In</Typography>

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

                <Button type="submit" variant="contained">
                    Submit
                </Button>
            </Box>

            <Typography sx={{ marginTop: "1rem" }}>
                Or
                {" "}
                <Link component={AppLink} to={`/${url.SIGN_UP}`}>sign up</Link>
                .
            </Typography>
        </form>
    );
};
