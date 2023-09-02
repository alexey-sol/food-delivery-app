type CreateOrderItemDto = {
    quantity: number;
    // productId: number;
    productId: number;
};

export type CreateOrderArg = {
    orderItems: CreateOrderItemDto[];
    storeId: number;
    userId: number;
};
