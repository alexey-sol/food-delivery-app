type CreateOrderItemDto = {
    quantity: number;
    productId: number;
};

export type CreateOrderArg = {
    orderItems: CreateOrderItemDto[];
    placeId: number;
    userId: number;
};
