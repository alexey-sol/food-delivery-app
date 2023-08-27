import type { User } from "features/user/models";
import type { Address } from "shared/models";
import type { HasId } from "shared/models/props";

// TODO move
export type City = HasId & {
    name: string;
};

export type CreateAddressDto = Pick<Address, "addressLine"> & {
    cityId: number;
};

export type SignUpDto = {
    address: CreateAddressDto;
    password: string;
    phone: string;
    username?: string;
};

export type SignInDto = {
    password: string;
    phone: string;
};

export type InitDto = {
    cities: City[];
    profile?: User;
};
