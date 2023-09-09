import {create} from 'zustand'
import {MessageInstance} from "antd/es/message/interface";

interface useMessageStore {
    messageApi: MessageInstance
    setMessageApi: (messageApi: MessageInstance) => void
}

export const useMessageStore = create<useMessageStore>((set) => ({
    messageApi: {} as MessageInstance,
    setMessageApi: (messageApi: MessageInstance) => set(() => ({messageApi}))
}))