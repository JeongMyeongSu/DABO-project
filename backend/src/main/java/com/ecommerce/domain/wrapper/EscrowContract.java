package com.ecommerce.domain.wrapper;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * TODO
 * Sub PJT Ⅲ
 * Escrow Smart Contract's Wrapper Class
 */

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.6.
 */
@SuppressWarnings("rawtypes")
public class EscrowContract extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516115a93803806115a9833981810160405260c081101561003357600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050856000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555083600260146101000a81548163ffffffff021916908363ffffffff16021790555082600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600460146101000a81548163ffffffff021916908363ffffffff160217905550426005819055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b1580156102a157600080fd5b505afa1580156102b5573d6000803e3d6000fd5b505050506040513d60208110156102cb57600080fd5b8101908080519060200190929190505050600681905550600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663517807e4306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b15801561038357600080fd5b505af1158015610397573d6000803e3d6000fd5b505050505050505050506111f9806103b06000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c806380e41ac11161008c578063b8f0994211610066578063b8f09942146102cb578063c19d93fb14610315578063ca6158cb14610341578063ea8a1af01461036b576100ea565b806380e41ac11461024d578063a035b1fe14610297578063b46300ec146102c1576100ea565b806357c4b262116100c857806357c4b262146101795780636177a9f8146101975780637022b58e146101e15780637150d8ae14610203576100ea565b806308551a53146100ef5780634c4cea5a1461013957806355be04ad14610157575b600080fd5b6100f7610375565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61014161039b565b6040518082815260200191505060405180910390f35b61015f6103a1565b604051808215151515815260200191505060405180910390f35b61018161060a565b6040518082815260200191505060405180910390f35b61019f610610565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101e9610636565b604051808215151515815260200191505060405180910390f35b61020b610acb565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610255610af1565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61029f610b16565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b6102c9610b2c565b005b6102d3610c97565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61031d610cbd565b6040518082600481111561032d57fe5b60ff16815260200191505060405180910390f35b610349610cd0565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b610373610ce6565b005b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60075481565b60008060048111156103af57fe5b600460189054906101000a900460ff1660048111156103ca57fe5b146103d457600080fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b15801561047457600080fd5b505afa158015610488573d6000803e3d6000fd5b505050506040513d602081101561049e57600080fd5b810190808051906020019092919050505090506014600460149054906101000a900463ffffffff160163ffffffff16811015610525576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602c815260200180611199602c913960400191505060405180910390fd5b6001600460186101000a81548160ff0219169083600481111561054457fe5b0217905550600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636452fca1306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b1580156105ea57600080fd5b505af11580156105fe573d6000803e3d6000fd5b50505050600191505090565b60055481565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461069257600080fd5b6002600481111561069f57fe5b600460189054906101000a900460ff1660048111156106ba57fe5b146106c457600080fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b15801561076457600080fd5b505afa158015610778573d6000803e3d6000fd5b505050506040513d602081101561078e57600080fd5b810190808051906020019092919050505090506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600460149054906101000a900463ffffffff166040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018263ffffffff16815260200192505050602060405180830381600087803b15801561088357600080fd5b505af1158015610897573d6000803e3d6000fd5b505050506040513d60208110156108ad57600080fd5b8101908080519060200190929190505050506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600460149054906101000a900463ffffffff1663ffffffff1684036040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b1580156109a357600080fd5b505af11580156109b7573d6000803e3d6000fd5b505050506040513d60208110156109cd57600080fd5b8101908080519060200190929190505050506003600460186101000a81548160ff021916908360048111156109fe57fe5b021790555042600781905550600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166312ad9f6e306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b158015610aab57600080fd5b505af1158015610abf573d6000803e3d6000fd5b50505050600191505090565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600460149054906101000a900463ffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610b8657600080fd5b60016004811115610b9357fe5b600460189054906101000a900460ff166004811115610bae57fe5b14610bb857600080fd5b6002600460186101000a81548160ff02191690836004811115610bd757fe5b0217905550600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638ff06ab7306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b158015610c7d57600080fd5b505af1158015610c91573d6000803e3d6000fd5b50505050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600460189054906101000a900460ff1681565b600260149054906101000a900463ffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610d8f5750600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610d9857600080fd5b60006004811115610da557fe5b600460189054906101000a900460ff166004811115610dc057fe5b1480610df1575060016004811115610dd457fe5b600460189054906101000a900460ff166004811115610def57fe5b145b610dfa57600080fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b158015610e9a57600080fd5b505afa158015610eae573d6000803e3d6000fd5b505050506040513d6020811015610ec457600080fd5b810190808051906020019092919050505090506014600460149054906101000a900463ffffffff160163ffffffff1681106110b9576000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16836040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015610fc357600080fd5b505af1158015610fd7573d6000803e3d6000fd5b505050506040513d6020811015610fed57600080fd5b810190808051906020019092919050505050600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663fa89401a306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b1580156110a057600080fd5b505af11580156110b4573d6000803e3d6000fd5b505050505b60048060186101000a81548160ff021916908360048111156110d757fe5b0217905550600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166382b46df4306040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b15801561117d57600080fd5b505af1158015611191573d6000803e3d6000fd5b505050505056fe47726561746572207468616e206f7220657161756c7320746f2032302043617368206e656365737361727921a265627a7a723158204e01b5a12724d42759840dad46459495994d57ad9326e7329d4fe1743f13841d64736f6c634300050c0032";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_CASHCONTRACT = "cashContract";

    public static final String FUNC_COMPLETEAT = "completeAt";

    public static final String FUNC_ITEMID = "itemId";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_PURCHASERECORDCONTRACT = "purchaseRecordContract";

    public static final String FUNC_PURCHASERECORDCONTRACTADDRESS = "purchaseRecordContractAddress";

    public static final String FUNC_PURCHASEDAT = "purchasedAt";

    public static final String FUNC_SELLER = "seller";

    public static final String FUNC_STATE = "state";

    public static final String FUNC_CHECKDEPOSIT = "checkDeposit";

    public static final String FUNC_SEND = "send";

    public static final String FUNC_CANCEL = "cancel";

    public static final String FUNC_CONFIRM = "confirm";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected EscrowContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EscrowContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EscrowContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EscrowContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> buyer() {
        final Function function = new Function(FUNC_BUYER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> cashContract() {
        final Function function = new Function(FUNC_CASHCONTRACT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> completeAt() {
        final Function function = new Function(FUNC_COMPLETEAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> itemId() {
        final Function function = new Function(FUNC_ITEMID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> price() {
        final Function function = new Function(FUNC_PRICE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContract() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContractAddress() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACTADDRESS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> purchasedAt() {
        final Function function = new Function(FUNC_PURCHASEDAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> seller() {
        final Function function = new Function(FUNC_SELLER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> state() {
        final Function function = new Function(FUNC_STATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> checkDeposit() {
        final Function function = new Function(
                FUNC_CHECKDEPOSIT,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> send() {
        final Function function = new Function(
                FUNC_SEND,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel() {
        final Function function = new Function(
                FUNC_CANCEL,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm() {
        final Function function = new Function(
                FUNC_CONFIRM,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EscrowContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EscrowContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EscrowContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EscrowContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EscrowContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EscrowContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EscrowContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EscrowContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EscrowContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_contractAddress),
                new Address(_purchaseRecordAddress),
                new Uint32(_itemId),
                new Address(_seller),
                new Address(_buyer),
                new Uint32(_price)));
        return deployRemoteCall(EscrowContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<EscrowContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_contractAddress),
                new Address(_purchaseRecordAddress),
                new Uint32(_itemId),
                new Address(_seller),
                new Address(_buyer),
                new Uint32(_price)));
        return deployRemoteCall(EscrowContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_contractAddress),
                new Address(_purchaseRecordAddress),
                new Uint32(_itemId),
                new Address(_seller),
                new Address(_buyer),
                new Uint32(_price)));
        return deployRemoteCall(EscrowContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_contractAddress),
                new Address(_purchaseRecordAddress),
                new Uint32(_itemId),
                new Address(_seller),
                new Address(_buyer),
                new Uint32(_price)));
        return deployRemoteCall(EscrowContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
