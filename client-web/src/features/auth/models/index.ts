import { type User } from "@/features/user/models";
import { type Address } from "@/shared/models";
import { type HasId } from "@/shared/models/props";

export type Locality = HasId & {
    name: string;
};

export type CreateAddressDto = Pick<Address, "addressLine">;

export type SignUpDto = {
    address: CreateAddressDto;
    localityId: number;
    password: string;
    phone: string;
    username?: string;
};

export type SignInDto = {
    password: string;
    phone: string;
};

export type InitDto = {
    cities: Locality[];
    profile?: User;
};
