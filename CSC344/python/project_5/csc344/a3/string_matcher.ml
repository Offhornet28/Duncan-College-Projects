(*
Duncan Zaug
Project 3
String Parser
*)

#load "str.cma"
open Str;;

(*** Scanner ***)

(* Scanner Types *)
type token = Tok_Char of char 
  | Tok_OR
  | Tok_Q
  | Tok_LPAREN
  | Tok_RPAREN
  | Tok_END

(* let re_char = Str.regexp "[o-9A-Za-z]" *)
let re_or = Str.regexp "|"
let re_q = Str.regexp "?"
let re_lParen = Str.regexp "("
let re_rParen = Str.regexp ")"

exception IllegalExpression of string

let tokenize str =
  let rec tok pos s =
    if pos >= String.length s then
      [Tok_END]
    else
      if (Str.string_match re_or s pos) then
        Tok_OR::(tok (pos+1) s)
      else if (Str.string_match re_q s pos) then
        Tok_Q::(tok (pos+1) s)
      else if (Str.string_match re_lParen s pos) then
        Tok_LPAREN::(tok (pos+1) s)
      else if (Str.string_match re_rParen s pos) then
        Tok_RPAREN::(tok (pos+1) s)
      else 
        let token = s in 
          (Tok_Char token.[pos])::(tok (pos+1) s)
  in
  tok 0 str

(*** Parser ***)
(* AST Types *)
type re = C of char
    | Concat of re * re
    | Optional of re
    | Alternation of re * re

let rec c_to_str c =
  match c with
    C s -> String.make 1 s
  | Concat(c1,c2) -> "(" ^ (c_to_str c1) ^ (c_to_str c2) ^ ")"
  | Optional(c1) -> "(" ^ (c_to_str c1) ^ "?" ^ ")"
  | Alternation(c1,c2) -> "(" ^ (c_to_str c1) ^ "|" ^ (c_to_str c2) ^ ")"
;;
 
let tok_list = ref [];;

exception ParseError of string

(*gets the head of the token list*)
let lookahead () =
  match !tok_list with
    [] -> raise (ParseError "no tokens")
  | (h::t) -> h

(* consumes one token *)
let match_tok a =
 match !tok_list with
  | (h::t) when a = h -> tok_list := t
  | _ -> raise (ParseError "bad match")

(*
 S  -: E$
 E  -: T '|' E | T
 T  -: F T | F
 F  -: A '?' | A
 A  -: C | '(' E ')'
 C  -: Alphanumeric characters plus '.' 
*)
let rec parse_S () = 
  let a1 = parse_E () in 
  match lookahead() with
  | Tok_END -> a1
  |_ -> raise (ParseError "parse_S")
and parse_E () = 
  let a1 = parse_T () in 
  let t = lookahead () in
  match t with
    Tok_OR -> match_tok Tok_OR ;
              let a2 = parse_E () in 
              Alternation(a1, a2)
    |_-> a1

and parse_T () =
  let a1 = parse_F () in
  let f = lookahead () in
  match f with
    Tok_Char f -> let a2 = parse_T () in
                  Concat(a1, a2)
    | Tok_LPAREN -> let a2 = parse_T () in
                    Concat(a1, a2)
    |_-> a1

and parse_F () =
  let a1 = parse_A () in
  let a = lookahead () in
  match a with
    Tok_Q -> match_tok Tok_Q ;
             Optional(a1)
    |_ -> a1

and parse_A () =
  let e = lookahead () in
  match e with 
    Tok_LPAREN -> match_tok Tok_LPAREN ;
                  let a1 = parse_E () in 
                  match_tok Tok_RPAREN ;
                  a1
    | Tok_Char c -> match_tok (Tok_Char c) ;
                    C c 
    |_ -> raise (ParseError "parse_A")
;;

let parse str =
  tok_list := (tokenize str);
  let re = parse_S () in
  if !tok_list <> [Tok_END] then
    raise (ParseError "parse error")
  else
    re
;;
(*** Matcher ***)
let i = ref 0;;
let rec matcher p s i =
  match p with
    C '.' -> incr i; true
    | C c -> if s.[!i] = c then (incr i ; true) else (false)
    | Concat (c1,c2) -> ((matcher c1 s i) && (matcher c2 s i))
    | Alternation (c1,c2) -> let oldi = !i in
                             if (matcher c1 s i) then (true)
                             else (i := oldi; (matcher c2 s i))
    | Optional (c1) -> let oldi = !i in 
                        if (matcher c1 s i) then (true) 
                        else (i := oldi ; true)
;;

(** main function **)
let main () =
  print_string "pattern? " ;
  let rawPatternInput = read_line () in
  let pattern = parse rawPatternInput in
  print_string "pattern= "; 
  print_endline (c_to_str pattern);
  let rec inf_loop() =
    print_string "String? " ;
    let str = read_line () in
    i := 0;
    if ((matcher pattern str i) && (!i = (String.length str))) then (print_endline "match") 
    else (print_endline "no match") ;
    if (!i >= (String.length str)) then i := 0 ;
    inf_loop()
  in
  inf_loop()
;;
main ();;
